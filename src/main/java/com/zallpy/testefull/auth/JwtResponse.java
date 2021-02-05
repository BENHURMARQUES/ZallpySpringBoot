/**
 * @author benhurmarques
 * 
 * Feb 4, 2021
 */
package com.zallpy.testefull.auth;

import java.io.Serializable;

/**
 * @author benhurmarques
 *
 */
public class JwtResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String jwttoken;
	private final String nome;

	public JwtResponse(String jwttoken, String nome) {
		this.jwttoken = jwttoken;
		this.nome = nome;
	}

	public String getToken() {
		return this.jwttoken;
	}

	public String getNome() {
		return this.nome;
	}

}
