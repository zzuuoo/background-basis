package com.zuovx.book.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zuoweixing
 * @date 2019-10-11 11:21
 */
@Data
@Component
public class AuthJwt {
	@Value("${authjwt.base64Secret}")
	private String base64Secret;
	@Value("${authjwt.TTLtimes}")
	private Long TTLTimes;

}
