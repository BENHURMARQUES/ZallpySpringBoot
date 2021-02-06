package com.zallpy.testefull.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final int CODIGO_HTTP_BUSINESS_EXCEPTION = 460;
	
	@Autowired
	MessageSource messageSource;

	/**
	 * Metodo responsavel por capturar qualquer excecao nao tratada.
	 * Realiza o envio para o jaegger.
	 * Retorna uma mensagem para a requisicao.
	 * 
	 * @param ex Exception
	 * @param request
	 * @return
	 */
	@ExceptionHandler({Exception.class})
	public ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
		String mensagem = "ocorreu_um_erro#1";
		Erro erro = new Erro();
		erro.getMensagens().add(mensagem);
	
		logger.error(mensagem);

		return handleExceptionInternal(ex, erro, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	/**
	 * Metodo responsavel por capturar qualquer excecao nao tratada.
	 * Realiza o envio para o jaegger.
	 * Retorna uma mensagem para a requisicao.
	 * 
	 * @param ex BusinessException
	 * @param request
	 * @return
	 */
	@ExceptionHandler({BusinessException.class})
	public ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
		String mensagem = (ex.getMessage() != null) ? ex.getMessage().toString() : "ocorreu_um_erro#2";
		Erro erro = new Erro();
		erro.getMensagens().add(mensagem);
		
		logger.error(mensagem);

		return ResponseEntity.status(CODIGO_HTTP_BUSINESS_EXCEPTION).body(erro);
	}

	/**
	 * Metodo que trata quando o json esperado venha errado na requisicao.
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String mensagem = messageSource.getMessage("mensagem_invalida", null, LocaleContextHolder.getLocale());
		Erro erro = new Erro();
		erro.getMensagens().add(mensagem);
		
		logger.error(mensagem);

		return handleExceptionInternal(ex, erro, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	/**
	 * Metodo que trata quando o tipo de argumento enviado nao e compativel com o metodo chamado
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler({MethodArgumentTypeMismatchException.class})
	public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
		String mensagem = messageSource.getMessage("erro_ao_converter_valor", null, LocaleContextHolder.getLocale());
		Erro erro = new Erro();
		erro.getMensagens().add(mensagem);
		
		logger.error(mensagem);
		
		return handleExceptionInternal(ex, erro, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	/**
	 * Erro de validacao em atributos recebidos por path e nao por objeto (parametros recebidos na url validados com @valid)
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler({ConstraintViolationException.class})
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
		
		logger.error(ex.getMessage());
		
		return handleExceptionInternal(ex, criarListaDeErros(ex.getConstraintViolations()), new HttpHeaders(),
				HttpStatus.BAD_REQUEST, request);
	}
	
	private Erro criarListaDeErros(Set<ConstraintViolation<?>> constraintViolation) {
		Erro erro = new Erro();

		for (ConstraintViolation<?> constrainViolation : constraintViolation) {
			String mensagem = (constrainViolation.getPropertyPath().toString().split("\\."))[1] + " " + constrainViolation.getMessage();
			erro.getMensagens().add(mensagem);
		}

		return erro;
	}
	
	/**
	 * Metodo chamado quando ocorre algum erro de validacao em um dto anotado com @valid ou @Valided quando esperado receber um DTO
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		logger.error(ex.getMessage());

		return super.handleExceptionInternal(ex, criarListaDeErros(ex.getBindingResult()), headers,
				HttpStatus.BAD_REQUEST, request);
	}

	private Erro criarListaDeErros(BindingResult bidingResult) {
		Erro erro = new Erro();

		for (FieldError fieldEror : bidingResult.getFieldErrors()) {
			String mensagem = messageSource.getMessage(fieldEror, LocaleContextHolder.getLocale());
			erro.getMensagens().add(mensagem);
		}

		return erro;
	}
	
	/**
	 * Metodo que trata excecao quando tentar deletar ou atualizar um recurso que nao existe na base de dados.
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		String mensagem = messageSource.getMessage("recurso_nao_encontrado", null, LocaleContextHolder.getLocale());
		Erro erro = new Erro();
		erro.getMensagens().add(mensagem);
		
		logger.error(mensagem);
		
		return super.handleExceptionInternal(ex, erro, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return errorResponse(HttpStatus.BAD_REQUEST, "Required request params missing");
	}	
		
	private ResponseEntity<Object> errorResponse(HttpStatus status, String message) {
		return ResponseEntity.status(status).body(message);
	}

	public class Erro {
		private List<String> mensagens;

		public List<String> getMensagens() {
			if(mensagens == null) {
				mensagens = new ArrayList<String>();
			}
			return mensagens;
		}

		public void setMensagens(List<String> mensagens) {
			this.mensagens = mensagens;
		}
	}
}
