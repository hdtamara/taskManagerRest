package com.taskmanagerrest.taskmanager.security.util;


import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    //Logger para mostrar los errores
    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);
   //Clave para verificar el token
    @Value("${jwt.secret}")
    private String secret;
    //tiempo base de expiracion
    @Value("${jwt.expiration}")
    private  int expiration;

    //genera el token en base a lo establecido en properties
    public String generateToken(Authentication authentication){
        UserDetails mainUser = (UserDetails) authentication.getPrincipal();
        System.out.println(mainUser);
        logger.error(mainUser.getUsername());
        return Jwts.builder().setSubject(mainUser.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+expiration*1000))
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    //permite obtener el nombre de usuario con el tocken
    public String getUserNameFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    //Permite validad el token con la firma secreta
    //Controlamos cualquier error que pueda existir con el token

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException e){
            logger.error("token mal formado");            
        }catch (UnsupportedJwtException e){
            logger.error("token no soportado");
        }catch (ExpiredJwtException e){
            logger.error("token expirado");
        }catch (IllegalArgumentException e){
            logger.error("token vacío");
        }catch (SignatureException e){
            logger.error("fail en la firma");
        }
        return false;
    }
}
