/**
 * @author benhurmarques
 * 
 * Feb 4, 2021
 */
package com.zallpy.testefull.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.zallpy.testefull.po.UsuarioPO;

/**
 * @author benhurmarques
 *
 */
public interface UsuarioRepository extends CrudRepository<UsuarioPO, Long> {


	@Query(value = "select * from usuario where username = :username limit 1 ", nativeQuery = true)
	UsuarioPO findNomeUsuario(@Param("username") String username);
	
}
