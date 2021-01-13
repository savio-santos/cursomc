package br.com.savio.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;

import br.com.savio.cursomc.services.exceptions.AuthorizationException;
import br.com.savio.cursomc.services.exceptions.FileException;
import br.com.savio.cursomc.services.exceptions.MyDataIntegrityViolationException;
import br.com.savio.cursomc.services.exceptions.ObjectNotFoundException;

/* classe responsavel por pegar as exptions do controller rest*/

@ControllerAdvice
public class ResouceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);

	}

	@ExceptionHandler(MyDataIntegrityViolationException.class)
	public ResponseEntity<StandardError> dataIntegrityViolation(MyDataIntegrityViolationException e,
			HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {

		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de Validação",
				System.currentTimeMillis());
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.AddError(x.getField(), x.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);

	}

	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorizationError(AuthorizationException e, HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);

	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<StandardError> accessDeniedException(AccessDeniedException e, HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);

	}

	@ExceptionHandler(FileException.class)
	public ResponseEntity<StandardError> fileException(FileException e, HttpServletRequest request) {
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);

	}

	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<StandardError> amazonServiceException(AmazonServiceException e, HttpServletRequest request) {
		HttpStatus code = HttpStatus.valueOf(e.getStatusCode());

		StandardError err = new StandardError(code.value(), e.getMessage(), System.currentTimeMillis());

		return ResponseEntity.status(code).body(err);

	}

	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<StandardError> amazonS3Exception(AmazonS3Exception e, HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);

	}

	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<StandardError> amazonClientException(AmazonClientException e, HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);

	}

//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<StandardError> exceptionError(Exception e, HttpServletRequest request) {
//
//		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
//				System.currentTimeMillis());
//
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
//
//	}

}
