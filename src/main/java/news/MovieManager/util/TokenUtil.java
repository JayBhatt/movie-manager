package news.MovieManager.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import news.MovieManager.model.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter
@Setter
@EqualsAndHashCode
public class TokenUtil {

    @Value("${token.ttl}")
    private int ttl;

    @Value("${token.secret}")
    private String secret;

    public Token parse(String token) {
        Token parsedToken = new Token();
        String key = Base64.getEncoder().encodeToString(this.secret.getBytes());
        final Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        parsedToken.setSubject(claims.getSubject());
        parsedToken.setExpiration(claims.getExpiration());
        return parsedToken;
    }

    private boolean hasTokenExpired(Token token) {
        return token.getExpiration().before(new Date());
    }

    public String generateToken(UserDetails user) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + this.ttl * 1000))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
    }

    public boolean validate(Token token, UserDetails user) {
        return token.getSubject().equals(user.getUsername()) && !this.hasTokenExpired(token);
    }

}
