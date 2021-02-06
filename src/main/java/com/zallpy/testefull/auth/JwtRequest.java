/**
 * @author benhurmarques
 * 
 * Feb 4, 2021
 */
package com.zallpy.testefull.auth;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * @author benhurmarques
 *
 */
public class JwtRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@NotNull(message = "{usuario_deve_ser_preenchido}")
	private String username;
	
	@NotNull(message = "{senha_deve_ser_preenchida}")
	private String password;

	public JwtRequest() {
	}

	public JwtRequest(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
