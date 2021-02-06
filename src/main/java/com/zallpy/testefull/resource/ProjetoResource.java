/**
 * @author benhurmarques
 * 
 * Feb 4, 2021
 */
package com.zallpy.testefull.resource;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zallpy.testefull.auth.JwtRequest;
import com.zallpy.testefull.auth.JwtTokenUtil;
import com.zallpy.testefull.dto.request.GravarHorasRequestDTO;
import com.zallpy.testefull.dto.response.ProjetoResponseDTO;
import com.zallpy.testefull.exceptions.ServiceException;
import com.zallpy.testefull.po.ProjetoPO;
import com.zallpy.testefull.po.RegistroHorasProjetoPO;
import com.zallpy.testefull.po.UsuarioPO;
import com.zallpy.testefull.repository.ProjetoRepository;
import com.zallpy.testefull.repository.RegistroHorasRepository;
import com.zallpy.testefull.repository.UsuarioRepository;

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
	RegistroHorasRepository registroHorasRepository;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@ApiOperation(value = "Listar Projetos")
	@GetMapping(value = "/listar", produces = "application/json")
	public ResponseEntity<List<ProjetoResponseDTO>> buscarProjetos(HttpServletRequest request) {

		UsuarioPO usuarioPO = retornarUsuarioPO(request);

		List<ProjetoResponseDTO> lista = new ArrayList<>();
		List<ProjetoPO> projetos = projetoRepository.buscaMeus(usuarioPO.getUsername());
		if (projetos != null) {
			for (ProjetoPO projeto : projetos) {
				ProjetoResponseDTO responseDTO = new ProjetoResponseDTO();
				responseDTO.setIdProjeto(projeto.getIdProjeto());
				responseDTO.setNome(projeto.getNome());

				System.out.println("##################################"+ usuarioPO.getIdUsuario() +" - "+ projeto.getIdProjeto());
				
				Double horas = 0.0;
				List<RegistroHorasProjetoPO> listHoras = registroHorasRepository
						.buscaMinhasHoras(usuarioPO.getIdUsuario(), projeto.getIdProjeto());
				if (listHoras.size() > 0) {
					System.out.println("##################################");
					for (RegistroHorasProjetoPO registroHoras : listHoras) {
						horas += registroHoras.getHoras();
					}
				}

				responseDTO.setHoras(horas);

				lista.add(responseDTO);
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(lista);
	}

	@ApiOperation(value = "Registro de horas")
	@PostMapping(value = "/registrohoras", produces = "application/json")
	public ResponseEntity<?> registrarHoras(@Validated @RequestBody GravarHorasRequestDTO gravarHorasRequestDTO,
			HttpServletRequest request) {

		UsuarioPO usuarioPO = retornarUsuarioPO(request);
		ProjetoPO projetoPO = projetoRepository.buscaPorId(gravarHorasRequestDTO.getIdProjeto(),
				usuarioPO.getUsername());
		if (projetoPO != null) {
			try {

				RegistroHorasProjetoPO registroHorasProjetoPO = new RegistroHorasProjetoPO();
				registroHorasProjetoPO.setIdProjeto(projetoPO.getIdProjeto());
				registroHorasProjetoPO.setIdUsuario(usuarioPO.getIdUsuario());
				registroHorasProjetoPO.setHoras(gravarHorasRequestDTO.getHoras());
				registroHorasProjetoPO.setData(converteData(gravarHorasRequestDTO.getData()));

				registroHorasRepository.save(registroHorasProjetoPO);
				return ResponseEntity.status(HttpStatus.CREATED).build();

			} catch (Exception e) {
				throw new ServiceException("Um erro ocorreu ao gravar o registro de horas!");
			}
		} else {
			throw new ServiceException("Projeto não encontrado ou não pertence ao usuário!");
		}

	}

	private Date converteData(String data) {
		String formato = "dd/MM/yyyy";
		try {
			Date date = new SimpleDateFormat(formato).parse(data);
			return date;
		} catch (ParseException e) {
			throw new ServiceException("Um erro ocorreu ao converter a data!");
		}
	}

	private UsuarioPO retornarUsuarioPO(HttpServletRequest request) {
		String usuario = retornaUsuario(request);
		return usuarioRepository.findNomeUsuario(usuario);
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
