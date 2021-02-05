/**
 * @author benhurmarques
 * 
 * Feb 4, 2021
 */
package com.zallpy.testefull.dto.response;

import java.io.Serializable;

/**
 * @author benhurmarques
 *
 */
public class ProjetoResponseDTO  implements Serializable {
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int idProjeto;
	private String nome;
	/**
	 * @return the idProjeto
	 */
	public int getIdProjeto() {
		return idProjeto;
	}
	/**
	 * @param idProjeto the idProjeto to set
	 */
	public void setIdProjeto(int idProjeto) {
		this.idProjeto = idProjeto;
	}
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	
	
	
	
}
