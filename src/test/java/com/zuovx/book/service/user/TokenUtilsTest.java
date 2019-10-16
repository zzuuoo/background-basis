package com.zuovx.book.service.user;

import com.zuovx.book.utils.TokenUtils;
import org.junit.Test;

/**
 * @author zuoweixing
 * @date 2019-10-11 11:29
 */
public class TokenUtilsTest {

	@Test
	public void parseJWT() {

	}

	@Test
	public void createJWT() {
		String token = TokenUtils.createJwt("zuo","2",null,null,null,
				1800000,"MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=");
		System.out.println(token);

		System.out.println(TokenUtils.parseJwt(token,"MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY="));;
	}
}