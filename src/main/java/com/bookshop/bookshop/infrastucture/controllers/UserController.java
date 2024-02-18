package com.bookshop.bookshop.infrastucture.controllers;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookshop.bookshop.core.coreServices.IAuthenticationService;
import com.bookshop.bookshop.core.coreServices.ICrateService;
import com.bookshop.bookshop.core.coreServices.IEmailService;
import com.bookshop.bookshop.core.coreServices.IJwtService;
import com.bookshop.bookshop.core.coreServices.IRefreshTokenService;
import com.bookshop.bookshop.core.coreServices.IUserService;
import com.bookshop.bookshop.core.mappers.UserModelMapper;
import com.bookshop.bookshop.core.models.Role;
import com.bookshop.bookshop.dtos.CrateModelDto;
import com.bookshop.bookshop.dtos.LogInModel;
import com.bookshop.bookshop.dtos.RefreshTokenModelDto;
import com.bookshop.bookshop.dtos.UserModelDto;
import com.bookshop.bookshop.infrastucture.services.JwtService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping("/user")
@RestController
public class UserController 
{
    @Autowired
    IUserService userService;
    @Autowired
    IJwtService jwtService;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    IAuthenticationService authenticationService;
    @Autowired 
    IRefreshTokenService refreshTokenService;
    @Autowired
    ICrateService crateService;
    @Autowired
    IEmailService emailService;


    @PostMapping("/register")
    // надо подумать как создать проверку на то что определенные поля уже заняты и как это отправлять на фронт
    // Написать объект отдельный для регистрации с boolean полями что есть,чего нет? Просто проверка на заполненность полей делается на фронте
    // хотя проверку на то что все обязательные поля заполнены тож надо сделать
    public ResponseEntity<String> UserRegistration(@RequestBody UserModelDto userModel, HttpServletResponse response) throws InterruptedException, ExecutionException // мб надо прописать отдельный класс для ответов, так как токен одни хрен в куках пищется
, MessagingException
    {
        if(userModel == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // указывет на то указаны ли при регистрации уже существующие в бд email и username
        String mistakes = "";
        
        try 
        {
            mistakes = authenticationService.SignUp(userModel).get();
        } 
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(!mistakes.isEmpty())
        {
            return new ResponseEntity<String>(mistakes.toString(), HttpStatus.BAD_REQUEST);
        }

        UUID userId = UUID.randomUUID();

        userModel.setRole(Role.ROLE_USER);
        userModel.setEmailVerificated(false);
        userModel.setId(userId);
        userModel.setVeridficationCode(RandomStringUtils.randomAlphanumeric(10));

        userService.CreateUser(userModel);//создаем модель пользователя
        crateService.CreateCrate(new CrateModelDto(userId)).get(); // создаем корзину пользователя
        emailService.sendVerificationEmail(userModel); // отправка на почту сообщения для подтверждения почты

        UUID tokenId = UUID.randomUUID();

        String accessToken =jwtService.GenerateAccessToken(UserModelMapper.AsEntity(userModel), tokenId);
        String refreshToken = jwtService.GenerateRefreshToken(UserModelMapper.AsEntity(userModel), tokenId);

        refreshTokenService.CreateToken(new RefreshTokenModelDto(tokenId, refreshToken,
                                     new Date(System.currentTimeMillis() +JwtService.refreshTokenExpitaion))).get();

        Cookie refreshCookie = new Cookie("refresh", refreshToken);
        refreshCookie.setHttpOnly(true);

        Cookie accessCookie = new Cookie("access", accessToken);
        accessCookie.setHttpOnly(true);

        response.addCookie(refreshCookie);
        response.addCookie(accessCookie);

        //потом просто пустоту в ответ давать будет, а токен он возвращает чтоб мне удобней было проверять
        return ResponseEntity.ok(accessToken+"\n\n "+userId.toString());
    }

    @PostMapping("/login/email")
    public ResponseEntity<String> LogInByEmail(@RequestBody LogInModel model, HttpServletResponse response) throws InterruptedException, ExecutionException
    {

        if(userService.GetUserByUserName(model.getLogin()) == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        model.setPassword(encoder.encode(model.getPassword()));
       

        if(!authenticationService.SingInByEmail(model).get())
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UUID tokenId = UUID.randomUUID();
        UserModelDto dto = userService.GetUserByUserName(model.getLogin()).get();
        
        String accessToken = jwtService.GenerateAccessToken(UserModelMapper.AsEntity(dto),tokenId);
        String refreshToken = jwtService.GenerateRefreshToken(UserModelMapper.AsEntity(dto), tokenId);

        refreshTokenService.CreateToken(new RefreshTokenModelDto(tokenId, refreshToken,
                                     new Date(System.currentTimeMillis() +JwtService.refreshTokenExpitaion))).get();

        Cookie refreshCookie = new Cookie("refresh", refreshToken);
        refreshCookie.setHttpOnly(true);

        Cookie accessCookie = new Cookie("access", accessToken);
        accessCookie.setHttpOnly(true);

        response.addCookie(refreshCookie);
        response.addCookie(accessCookie);


        return ResponseEntity.ok(accessToken);
    }

    @PostMapping("/login/username")
    public ResponseEntity<String> LogInByUsername(@RequestBody LogInModel model, HttpServletResponse response) throws InterruptedException, ExecutionException
    {

        if( userService.GetUserByUserName(model.getLogin()).get()== null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        model.setPassword(encoder.encode(model.getPassword()));

        if(!authenticationService.SignInByLogin(model).get())
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserModelDto dto = userService.GetUserByUserName(model.getLogin()).get();
        UUID tokenId = UUID.randomUUID();

        String accessToken = jwtService.GenerateAccessToken(UserModelMapper.AsEntity(dto), tokenId);
        String refreshToken = jwtService.GenerateRefreshToken(UserModelMapper.AsEntity(dto), tokenId);

        refreshTokenService.CreateToken(new RefreshTokenModelDto(tokenId, refreshToken, 
                                        new Date(System.currentTimeMillis() +JwtService.refreshTokenExpitaion))).get();

        Cookie refreshCookie = new Cookie("refresh", refreshToken);
        refreshCookie.setHttpOnly(true);

        Cookie accessCookie = new Cookie("access", accessToken);
        accessCookie.setHttpOnly(true);

        response.addCookie(refreshCookie);
        response.addCookie(accessCookie);

        return ResponseEntity.ok(accessToken);
    }

    @GetMapping("/user")
    public ResponseEntity<UserModelDto> GetUserByUsername(String username) throws InterruptedException, ExecutionException
    {
        return ResponseEntity.ok(userService.GetUserByUserName(username).get());
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> RefreshAccessToken(String refreshToken ,
                                                    @RequestHeader("Authorization")String token, 
                                                    HttpServletResponse response) throws InterruptedException, ExecutionException
    {   
        
        String accessToken = token.split("Bearer ")[1];
        
        // проверка на валидность access token
        if(!jwtService.IsTokenValidNoTime(accessToken))
        {
            Cookie refreshCookie = new Cookie("refresh", null);
            refreshCookie.setHttpOnly(true);
            refreshCookie.setMaxAge(0);

            Cookie accessCookie = new Cookie("access", null);
            accessCookie.setHttpOnly(true);
            accessCookie.setMaxAge(0);

            System.out.println("token invalid");

            response.addCookie(refreshCookie);
            response.addCookie(accessCookie);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UUID tokenId = UUID.fromString(jwtService.ExtractTokenId(accessToken));

        RefreshTokenModelDto refreshDto = refreshTokenService.GetTokenById(tokenId).get();
        
        // если в бд нет токена по такому id тогда удаляем куки
        if(refreshToken == null)
        {
            System.out.println("refresh is null");
            Cookie refreshCookie = new Cookie("refresh", null);
            refreshCookie.setHttpOnly(true);
            refreshCookie.setMaxAge(0);

            Cookie accessCookie = new Cookie("access", null);
            accessCookie.setHttpOnly(true);
            accessCookie.setMaxAge(0);

            response.addCookie(refreshCookie);
            response.addCookie(accessCookie);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        System.out.println( refreshToken);
        System.out.println(refreshDto.getToken());
        // Если токен полученный из бд неравен токену из запроса тогда удаляем куки
        if(!refreshDto.getToken().equals(refreshToken))
        {
            System.out.println("refreshes not equals");
            Cookie refreshCookie = new Cookie("refresh", null);
            refreshCookie.setHttpOnly(true);
            refreshCookie.setMaxAge(0);

            Cookie accessCookie = new Cookie("access", null);
            accessCookie.setHttpOnly(true);
            accessCookie.setMaxAge(0);

            response.addCookie(refreshCookie);
            response.addCookie(accessCookie);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // если токен истек
        if(refreshDto.getExpiredDate().before(new Date(System.currentTimeMillis())))
        {
            System.out.println("refresh expired");
            Cookie refreshCookie = new Cookie("refresh", null);
            refreshCookie.setHttpOnly(true);
            refreshCookie.setMaxAge(0);

            Cookie accessCookie = new Cookie("access", null);
            accessCookie.setHttpOnly(true);
            accessCookie.setMaxAge(0);

            response.addCookie(refreshCookie);
            response.addCookie(accessCookie);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserModelDto dto = userService.GetUserById(UUID.fromString(jwtService.ExtractId(accessToken))).get();

        refreshTokenService.DeleteTokenById(tokenId).get();

        tokenId = UUID.randomUUID();

        accessToken = jwtService.GenerateAccessToken(UserModelMapper.AsEntity(dto), tokenId);
        refreshToken = jwtService.GenerateRefreshToken(UserModelMapper.AsEntity(dto), tokenId);

        refreshTokenService.CreateToken(new RefreshTokenModelDto(tokenId, refreshToken, 
                                        new Date(System.currentTimeMillis() +JwtService.refreshTokenExpitaion))).get();

        Cookie refreshCookie = new Cookie("refresh", refreshToken);
        refreshCookie.setHttpOnly(true);

        Cookie accessCookie = new Cookie("access", accessToken);
        accessCookie.setHttpOnly(true);

        response.addCookie(refreshCookie);
        response.addCookie(accessCookie);

        return ResponseEntity.ok(accessToken);
    }

    @PostMapping("/forgot")
    public ResponseEntity<String> ForgotPassword(String username) throws InterruptedException, ExecutionException, MessagingException
    {
        UserModelDto modelDto = userService.GenerateNewPassword(username).get(); // получаем dto юзера с новым паролем

        String password = modelDto.getPassword();

        modelDto.setPassword(encoder.encode(password)); // кодируем пароль


        //Отправка пароля на почту
        emailService.sendEmailWithPassword(modelDto, password);
        //
        return ResponseEntity.ok(password); // потом уберу
    }

    @PostMapping("/logout")
    public ResponseEntity<String> LogOut(String accessToken, HttpServletResponse response)
    {
        // получение uuid токена
        UUID tokenId = UUID.fromString(jwtService.ExtractTokenId(accessToken));
        
        refreshTokenService.DeleteTokenById(tokenId);

        Cookie refreshCookie = new Cookie("refresh", "");
        refreshCookie.setHttpOnly(true);
        refreshCookie.setMaxAge(0);

        Cookie accessCookie = new Cookie("access", "");
        accessCookie.setHttpOnly(true);
        accessCookie.setMaxAge(0);

        response.addCookie(refreshCookie);
        response.addCookie(accessCookie);

        return ResponseEntity.ok("logged out");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> DeleteUserById(String accessToken) throws InterruptedException, ExecutionException
    {
        UUID userId = UUID.fromString(jwtService.ExtractId(accessToken));

        if(userService.GetUserById(userId).get() == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        boolean isUserDeleted = userService.DeleteUserById(userId).get();

        if(!isUserDeleted)
        {
            return new ResponseEntity<>(HttpStatus.INSUFFICIENT_STORAGE);
        }

        return ResponseEntity.ok("Удалено");
    }

    //обновляем все(почти) кроме пароля
    @PutMapping("/update")
    public ResponseEntity<String> UpdateUserInfo(@RequestBody UserModelDto updatableModel) throws InterruptedException, ExecutionException
    {
        System.out.println(updatableModel.getId());
        UserModelDto userModel = userService.GetUserById(updatableModel.getId()).get();

        if(userModel == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userModel.setName(updatableModel.getName());
        userModel.setUserSurname(updatableModel.getUserSurname());
        
        if(!userService.UpdateUser(userModel).get())
        {
            return new ResponseEntity<>(HttpStatus.INSUFFICIENT_STORAGE);
        }

        return ResponseEntity.ok("updated");
    }

    // обновление чисто пароля
    @PutMapping("/update/password")
    public ResponseEntity<String> UpdateUserPassword(UUID userId, String oldPassword, String newPassword) throws InterruptedException, ExecutionException
    {
        UserModelDto userModel = userService.GetUserById(userId).get();

        if(!encoder.matches(oldPassword, userModel.getPassword()))
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        userModel.setPassword(encoder.encode(newPassword));
        
        if(userService.UpdateUser(userModel).get())
        {
            return new ResponseEntity<>(HttpStatus.INSUFFICIENT_STORAGE);
        }

        return ResponseEntity.ok("password changed");
    }

}
