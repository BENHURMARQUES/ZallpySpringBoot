/**
 * @author benhurmarques
 * 
 * Feb 6, 2021
 */
package com.zallpy.testefull.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.zallpy.testefull.po.RegistroHorasProjetoPO;

/**
 * @author benhurmarques
 *
 */
public interface RegistroHorasRepository extends CrudRepository<RegistroHorasProjetoPO, Long> {

	@Query(value = "select * from registro_horas_projeto where id_projeto = :idProjeto and id_usuario = :idUsuario order by data ", nativeQuery = true)
	List<RegistroHorasProjetoPO> buscaMinhasHoras(@Param("idUsuario") int idUsuario, @Param("idProjeto") int idProjeto);

	@Query(value = "select * from registro_horas_projeto where id_projeto = :idProjeto order by data ", nativeQuery = true)
	List<RegistroHorasProjetoPO> buscaHorasPorProjeto(@Param("idProjeto") int idProjeto);

}
