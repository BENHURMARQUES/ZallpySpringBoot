/**
 * @author benhurmarques
 * 
 * Feb 6, 2021
 */
package com.zallpy.testefull.exceptions;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * @author benhurmarques
 *
 */
public class BusinessException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	private String msg;

	public BusinessException(String msg) {
		this.msg = msg;
	}

	
	public String getMsg() {
		JsonObject json = new JsonObject();
		json.addProperty("message", msg);
		return new Gson().toJson(json);
	}

}
