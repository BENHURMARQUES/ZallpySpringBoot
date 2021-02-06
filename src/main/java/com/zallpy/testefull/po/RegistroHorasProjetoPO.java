/**
 * @author benhurmarques
 * 
 * Feb 4, 2021
 */
package com.zallpy.testefull.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author benhurmarques
 *
 */
@Entity
@Table(name = "registro_horas_projeto")
public class RegistroHorasProjetoPO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_registro_horas_projeto")
	private int idProjetoHorasProjetos;

	@Column(name = "id_projeto")
	private int idProjeto;

	@Column(name = "id_usuario")
	private int idUsuario;

	@Column(name = "data")
	private Date data;

	@ManyToOne(optional = true)
	@JoinColumn(name = "id_usuario", updatable = false, insertable = false)
	private UsuarioPO usuarioPO;

	@ManyToOne(optional = true)
	@JoinColumn(name = "id_projeto", updatable = false, insertable = false)
	private ProjetoPO projetoPO;

	@Column(name = "horas", nullable = true)
	private Double horas;

	/**
	 * @return the idProjetoHorasProjetos
	 */
	public int getIdProjetoHorasProjetos() {
		return idProjetoHorasProjetos;
	}

	/**
	 * @param idProjetoHorasProjetos the idProjetoHorasProjetos to set
	 */
	public void setIdProjetoHorasProjetos(int idProjetoHorasProjetos) {
		this.idProjetoHorasProjetos = idProjetoHorasProjetos;
	}

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
	 * @return the idUsuario
	 */
	public int getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return the data
	 */
	public Date getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Date data) {
		this.data = data;
	}

	/**
	 * @return the usuarioPO
	 */
	public UsuarioPO getUsuarioPO() {
		return usuarioPO;
	}

	/**
	 * @param usuarioPO the usuarioPO to set
	 */
	public void setUsuarioPO(UsuarioPO usuarioPO) {
		this.usuarioPO = usuarioPO;
	}

	/**
	 * @return the projetoPO
	 */
	public ProjetoPO getProjetoPO() {
		return projetoPO;
	}

	/**
	 * @param projetoPO the projetoPO to set
	 */
	public void setProjetoPO(ProjetoPO projetoPO) {
		this.projetoPO = projetoPO;
	}

	/**
	 * @return the horas
	 */
	public Double getHoras() {
		return horas;
	}

	/**
	 * @param horas the horas to set
	 */
	public void setHoras(Double horas) {
		this.horas = horas;
	}

}
