/**
 * @author benhurmarques
 * 
 * Feb 4, 2021
 */
package com.zallpy.testefull.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zallpy.testefull.auth.JwtRequest;
import com.zallpy.testefull.auth.JwtResponse;
import com.zallpy.testefull.auth.JwtTokenUtil;
import com.zallpy.testefull.auth.JwtUserDetailsService;
import com.zallpy.testefull.exceptions.ServiceException;
import com.zallpy.testefull.po.UsuarioPO;
import com.zallpy.testefull.repository.UsuarioRepository;
import com.zallpy.testefull.util.PasswordUtils;

import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author benhurmarques
 *
 */
@RestController
@CrossOrigin(origins = "*")
public class LoginResource {

	private static final Logger logger = LoggerFactory.getLogger(LoginResource.class);

	@Autowired
	JwtUserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@ApiOperation(value = "Login de usuario", response = JwtResponse.class)
	@PostMapping(value = "/authenticate", produces = "application/json")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authenticationRequest) throws Exception {

		logger.info("######### LOGIN #########");

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername()));

	}

	private void authenticate(String username, String password) throws Exception {
		try {

			UsuarioPO usuarioPO = usuarioRepository.findNomeUsuario(username);
			if (usuarioPO != null) {
				if (!PasswordUtils.verifyUserPassword(password, usuarioPO.getSenha(), "salt")) {
					throw new ServiceException("USUARIO E/OU SENHA NAO CONFERE.");
				}
			} else {
				throw new ServiceException("USUARIO E/OU SENHA NAO CONFERE..");
			}

		} catch (Exception e) {
			throw new Exception("ERRO INESPERADO AO EXECUTAR LOGIN", e);
		}
	}

}
