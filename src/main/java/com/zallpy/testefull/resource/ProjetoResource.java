/**
 * @author benhurmarques
 * 
 * Feb 4, 2021
 */
package com.zallpy.testefull.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zallpy.testefull.auth.JwtTokenUtil;
import com.zallpy.testefull.dto.response.ProjetoResponseDTO;
import com.zallpy.testefull.exceptions.ServiceException;
import com.zallpy.testefull.po.ProjetoPO;
import com.zallpy.testefull.repository.ProjetoRepository;
import io.swagger.annotations.ApiOperation;

/**
 * @author benhurmarques
 *
 */
@RestController()
@RequestMapping("/projetos")
@CrossOrigin(origins = "*")
public class ProjetoResource {

	private static final Logger logger = LoggerFactory.getLogger(ProjetoResource.class);

	@Autowired
	ProjetoRepository projetoRepository;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@ApiOperation(value = "Listar Projetos")
	@GetMapping(value = "/listar", produces = "application/json")
	public ResponseEntity<List<ProjetoResponseDTO>> buscarProjetos(HttpServletRequest request) {

		String usuario = retornaUsuario(request);

		List<ProjetoResponseDTO> lista = new ArrayList<>();
		List<ProjetoPO> projetos = projetoRepository.buscaMeus(usuario);
		if (projetos != null) {
			for (ProjetoPO projeto : projetos) {
				ProjetoResponseDTO responseDTO = new ProjetoResponseDTO();
				responseDTO.setIdProjeto(projeto.getIdProjeto());
				responseDTO.setNome(projeto.getNome());
				lista.add(responseDTO);
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(lista);
	}

	private String retornaUsuario(HttpServletRequest request) {
		try {
			String requestTokenHeader = request.getHeader("Authorization");
			if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
				requestTokenHeader = requestTokenHeader.substring(7);
				return jwtTokenUtil.getUsernameFromToken(requestTokenHeader);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		throw new ServiceException("Um erro ocorreu ao converter o usuario ");
	}

}
