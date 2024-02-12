package com.bookshop.bookshop.configs;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bookshop.bookshop.core.coreRepositories.IUserRepo;
import com.bookshop.bookshop.core.coreServices.IJwtService;
import com.bookshop.bookshop.core.models.UserModel;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
    
    @Autowired
    private IJwtService jwtService; 
    @Autowired
    private IUserRepo userRepo;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, 
            @NonNull HttpServletResponse response, 
            @NonNull FilterChain filterChain)
                throws ServletException, IOException 
    {
        String authHeader = request.getHeader(HEADER_NAME);
        if(StringUtils.isEmpty(authHeader) || !authHeader.startsWith(BEARER_PREFIX))
        {
            filterChain.doFilter(request, response);
            return;
        }

        

        System.out.println(authHeader);
        String jwt = authHeader.substring(BEARER_PREFIX.length());
        String username = "";

        try
        {
            username = jwtService.ExtractUserName(jwt);
        }
        catch(ExpiredJwtException e)
        {
            response.sendError(403, "token expired");
            return;
        }
        catch(SignatureException e)
        {
            response.sendError(403, "token not valid");
            return;
        }
        
        // String id = jwtService.ExtractId(jwt);
        
        if(StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            UserModel userModel = null;

                // userModel = UserModelMapper.AsEntity(userService.GetUserById(UUID.fromString(id)).get());
                userModel = userRepo.UserByUsername(username);
           

            if(jwtService.IsTokenValid(jwt, userModel))
            {
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken
                    (userModel,
                    null,
                    userModel.getAuthorities());
                
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.getContext().setAuthentication(authToken);;
            }
            
            filterChain.doFilter(request, response);
        }

    }

}
