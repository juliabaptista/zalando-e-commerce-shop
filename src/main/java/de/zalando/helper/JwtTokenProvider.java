package de.zalando.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenProvider {

  private static final String SECRET_KEY = "DyepD0U3QzPmK9b0qNmLznHFs8/tT/a1zxXbdZiodpaGmpjpcxl+ni8CG2hql5qKcbf1QFgJl/25uIDtHjFELIrCfCvcf6ZvnB5hE8Wu8a9l4nNcAM+pl3Jpv3GYsK+lG0hB2+m9IgjFTCIm/53qWQN+gi3IZbUGP99ZCyJOP6trodmP6gPoO8stFl17hPRkatTef+Pbb0EHpPf9HPL4fxcG2TAL+TXI/E1IHb1+irbris6qYeQPiAVlmHeEpXNUYlQGb/WuI8Rkg4+jxoCJgW0MTyiehlb1U5cXosHoNFhU+k7YhZqOPdl66Wi2fexYPG2ze6wUhm+Bz7MTqW59TrxSAJWTIZYyvZhAfczwh60=";
  private final String ISSUER = "JULIA_BAPTISTA_ECOMMERCE_IN-PARTNERSHIP-WITH-ZALANDO";

  public String extractUserName(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  private String generateToken(
      Map<String, Object> extraClaims,
      UserDetails userDetails) {
    return Jwts
        .builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) //valid for 24hrs + 1000 milliseconds
        .setIssuer(ISSUER)
        .signWith(SignatureAlgorithm.HS256, getSignInKey()).compact();
  }

  public Boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUserName(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  private Boolean isTokenExpired(String token) {
    return extractExpirationDate(token).before(new Date());
  }

  public Date extractExpirationDate(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
        .parser()
        .setSigningKey(getSignInKey())
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

}
