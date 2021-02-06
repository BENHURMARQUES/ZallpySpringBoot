package com.zallpy.testefull.exceptions;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String msg;

	public ServiceException(String msg) {
		this.msg = msg;
	}

	
	public String getMsg() {
		JsonObject json = new JsonObject();
		json.addProperty("message", msg);
		return new Gson().toJson(json);
	}
}