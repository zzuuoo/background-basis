package com.zuovx.book.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * @author zuoweixing
 * @date 2019-10-10 19:08
 */
@Slf4j
public class TokenUtils {



	/**
	 * 解析jwt
	 */
	public static Claims parseJwt(String jsonWebToken, String base64Security){
		try
		{
			return Jwts.parser()
					.setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
					.parseClaimsJws(jsonWebToken).getBody();
		}
		catch(Exception ex)
		{
			log.info(ex.getMessage());
			return null;
		}
	}

	/**
	 * 生成jwt
	 */
	public static String createJwt(String account, String userId, String role,
								   String audience, String issuer, long TTLMillis, String base64Security)
	{
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		//生成签名密钥
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		//添加构成JWT的参数
		JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
				.claim("role", role)
				.claim("account", account)
				.claim("userId", userId)
				.setIssuer(issuer)
				.setAudience(audience)
				.signWith(signatureAlgorithm, signingKey);
		//添加Token过期时间
		if (TTLMillis >= 0) {
			long expMillis = nowMillis + TTLMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp).setNotBefore(now);
		}

		//生成JWT
		return builder.compact();
	}
}
