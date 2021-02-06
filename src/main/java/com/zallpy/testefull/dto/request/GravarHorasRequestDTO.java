/**
 * @author benhurmarques
 * 
 * Feb 5, 2021
 */
package com.zallpy.testefull.dto.request;

import java.io.Serializable;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author benhurmarques
 *
 */

public class GravarHorasRequestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull(message = "{projeto_id_deve_ser_preenchido}")
	@Min(value = 1, message = "{projeto_id_deve_ser_preenchido}")
	private int idProjeto;

	@NotNull(message = "{horas_trabalhadas_deve_preenchido}")
	@Digits(integer = 2, fraction = 2, message = "{valor_hora_invalido}")
	@DecimalMin(value = "0.1", inclusive = false, message = "{valor_minimo_horas}")
	private Double horas;

	@NotNull(message = "{data_deve_ser_preenchida}")
	private String data;

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

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

}
