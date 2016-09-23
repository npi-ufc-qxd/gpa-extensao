package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.PAGINA_ERRO_500;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class ErrorsController {

	@ExceptionHandler(MultipartException.class)
	public String handleFileException(HttpServletRequest req, Exception e) {
		return PAGINA_ERRO_500;
	}
}
