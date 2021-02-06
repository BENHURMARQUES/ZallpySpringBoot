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

	@Query(value = "select * from registro_horas_projeto where id_projeto in (select id_projeto from usuarios_has_projeto where id_usuario = :idUsuario ) and id_projeto = :idProjeto ", nativeQuery = true)
	List<RegistroHorasProjetoPO> buscaMinhasHoras(@Param("idUsuario") int idUsuario, @Param("idProjeto") int idProjeto);

}
