package com.zuovx.service.bus;

import lombok.Data;

/**
 * @author zuoweixing
 * @date 2019-10-10 17:45
 */
@Data
public class JsonResult {
	private String msg;
	private int status;
	private Object data;
}
