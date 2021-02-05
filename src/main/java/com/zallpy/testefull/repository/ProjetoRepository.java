/**
 * @author benhurmarques
 * 
 * Feb 5, 2021
 */
package com.zallpy.testefull.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.zallpy.testefull.po.ProjetoPO;

/**
 * @author benhurmarques
 *
 */
public interface ProjetoRepository extends CrudRepository<ProjetoPO, Long> {

	@Query(value = " select * from projeto where id_projeto "
			+ " in (select id_projeto from usuarios_has_projeto where id_usuario in "
			+ " (select id_usuario from usuario where username = :username )) "
			+ " order by nome ", nativeQuery = true)
	List<ProjetoPO> buscaMeus(@Param("username") String usuario);

}
