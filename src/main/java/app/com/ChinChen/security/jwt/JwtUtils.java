package app.com.ChinChen.security.jwt;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import app.com.ChinChen.security.services.EmployeeDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	private String jwtSecret = "ChinChenSecretKey";

	private long jwtExpirationMs = 24 * 60 * 60 * 1000;

	public String generateJwtToken(EmployeeDetailsImpl employeePrincipal) {

		return Jwts.builder()
				.setSubject((employeePrincipal.getId()+"@@@"+employeePrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String getAccountFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}

	public String getLoginId(HttpServletRequest request){
		String headerAuth = request.getHeader("Authorization");

		if (headerAuth.startsWith("Bearer ")) {
			headerAuth = headerAuth.substring(7, headerAuth.length());
		}

		String loginId = getAccountFromJwtToken(headerAuth);
		loginId = loginId.split("@@@")[0];

		return loginId;
	}
}