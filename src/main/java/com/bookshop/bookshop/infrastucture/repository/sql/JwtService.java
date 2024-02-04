package com.bookshop.bookshop.infrastucture.repository.sql;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import com.bookshop.bookshop.core.coreServices.IJwtService;
import com.bookshop.bookshop.core.models.UserModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.lang.Function;

@PropertySource("security.properties")
public class JwtService implements IJwtService
{
    @Autowired
    Environment env;

    @Override
    public String ExtractUserName(String token) 
    {
        return ExtractClaims(token, Claims::getSubject);
    }

    @Override
    public String ExtractEmail(String token) 
    {
        return ExtractClaims(token, null);
    }

    @Override
    public String GenerateToken(UserModel user) 
    {
        Map<String, Object> claims = new HashMap<>();

        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());

        return GenerateToken(claims, user);
    }

    @Override
    public boolean IsTokenValid(String token, UserModel user) 
    {
        final String username = ExtractUserName(token);
        return (username.equals(user.getUsername())) && !IsTokenExpired(token); 
    }
    
    private String GenerateToken(Map<String, Object> extraClaims, UserModel user)
    {
        return Jwts.builder().claims(extraClaims).subject(user.getUsername())
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))
                    .signWith(GetKey(), Jwts.SIG.HS512)
                    .compact();
    }


    private <T> T ExtractClaims(String token, Function<Claims, T> claimsResolver)
    {
        final Claims claims = ExrtactAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims ExrtactAllClaims(String string)
    {
        return Jwts.parser().verifyWith(GetKey()).build().parseSignedClaims(string).getPayload();
    }

    private SecretKey GetKey()
    {
        return new SecretKeySpec(
            Base64.getDecoder().decode(env.getProperty("jwt.secret")), 
            "HmacSHA512");
    }

    private boolean IsTokenExpired(String token)
    {
        return ExtractExpiration(token).before(new Date());
    }

    private Date ExtractExpiration(String token)
    {
        return ExtractClaims(token, Claims::getExpiration);
    }
}
