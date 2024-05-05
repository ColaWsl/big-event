package com.wangsl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

	private static final String SECRET = "test";

	/**
	 * 生成token
	 */
	@Test
	public void testJwt(){
		Map<String, Object> claims = new HashMap<>();
		claims.put("username", "wangsl");
		claims.put("logo", "@@@#@@@");
		String token = JWT.create().withClaim("user", claims)
				.withExpiresAt(new Date(System.currentTimeMillis() + 10))
				.sign(Algorithm.HMAC256(SECRET));
		System.out.println(token);
	}

	@Test
	public void parseJwt(){
		String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImxvZ28iOiJAQEAjQEBAIiwidXNlcm5hbWUiOiJ3YW5nc2wifSwiZXhwIjoxNzE0OTAzOTg3fQ.cljNAazMISzi7hZKma0DuOGoaJi594ulmjgMx65n9_E";
		JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
		DecodedJWT decodedJWT = jwtVerifier.verify(token);
		Map<String, Claim> claims = decodedJWT.getClaims();
		System.out.println(claims.get("user"));
	}
}
