/**
 * @author benhurmarques
 * 
 * Feb 4, 2021
 */
package com.zallpy.testefull.auth;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zallpy.testefull.po.UsuarioPO;
import com.zallpy.testefull.repository.UsuarioRepository;

/**
 * @author benhurmarques
 *
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {

		UsuarioPO usuarioPO = usuarioRepository.findNomeUsuario(usuario);
		if (usuarioPO != null) {
			return new User(usuarioPO.getUsername(), usuarioPO.getSenha(), new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("Usuario nao encontrado: " + usuario);
		}

	}

}
