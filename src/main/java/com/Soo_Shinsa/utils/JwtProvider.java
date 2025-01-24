package com.Soo_Shinsa.utils;

import com.Soo_Shinsa.utils.user.model.User;
import com.Soo_Shinsa.utils.user.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

/**
 * JWT 제공자.
 * <p>토큰의 생성, 추출, 만료 확인 등의 기능.</p>
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {

    /**
     * JWT 시크릿 키.
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * 토큰 만료시간(밀리초).
     */
    @Getter
    @Value("${jwt.expiry-millis}")
    private long expiryMillis;

    private final UserRepository userRepository;

    /**
     * 인증 정보를 이용해 토큰 생성.
     *
     * @param authentication 인증 정보
     * @return 생성된 토큰
     * @throws EntityNotFoundException 입력받은 사용자 이름에 해당하는 사용자를 찾지 못했을 경우
     */
    public String generateToken(Authentication authentication) throws EntityNotFoundException {
        String username = authentication.getName();
        return this.generateTokenBy(username);
    }

    /**
     * 토큰에서 사용자 이름 추출.
     *
     * @param token 토큰
     * @return 사용자 이름
     */
    public String getUsername(String token) {
        Claims claims = this.getClaims(token);
        return claims.getSubject();
    }

    public boolean validToken(String token) throws JwtException {
        try {
            return !this.tokenExpired(token);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        }

        return false;
    }

    /**
     * 토큰 생성.
     *
     * @param email 사용자 이메일
     * @return 생성된 토큰
     * @throws EntityNotFoundException 해당 이메일에 해당하는 사용자를 찾지 못했을 경우
     */
    private String generateTokenBy(String email) throws EntityNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("해당 email에 맞는 값이 존재하지 않습니다."));
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + this.expiryMillis);

        return Jwts.builder()
                .subject(email)
                .issuedAt(currentDate)
                .expiration(expireDate)
                .claim("role", user.getRole())
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * 토큰에서 claim 추출.
     *
     * @param token 토큰
     * @return 클레임
     */
    private Claims getClaims(String token) {
        if (!StringUtils.hasText(token)) {
            throw new MalformedJwtException("토큰이 비어 있습니다.");
        }

        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 토큰이 만료되었는지 확인.
     *
     * @param token 토큰
     * @return 만료 여부
     */

    private boolean tokenExpired(String token) {
        final Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 입력 받은 토큰의 만료일을 리턴.
     *
     * @param token 토큰
     * @return 만료일
     */
    private Date getExpirationDateFromToken(String token) {
        return this.resolveClaims(token, Claims::getExpiration);
    }

    /**
     * 토큰에서 클레임 추출.
     *
     * @param token          토큰
     * @param claimsResolver 클레임 추출 함수
     * @param <T>            클레임 타입
     * @return 클레임
     */
    private <T> T resolveClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.getClaims(token);
        return claimsResolver.apply(claims);
    }
}
