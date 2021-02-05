/**
 * @author benhurmarques
 * 
 * Feb 4, 2021
 */
package com.zallpy.testefull.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author benhurmarques
 *
 */

@Entity
@Table(name = "projeto")
public class ProjetoPO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_projeto")
	private int idProjeto;

	@Column(name = "nome")
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
