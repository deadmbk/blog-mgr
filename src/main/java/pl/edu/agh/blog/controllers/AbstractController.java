package pl.edu.agh.blog.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class AbstractController {

	Logger log = Logger.getLogger(AbstractController.class);

	@ExceptionHandler(AccessDeniedException.class)
	public void handleError(HttpServletRequest request, HttpServletResponse response, Exception accessDeniedException) throws ServletException, IOException {
		
		log.error("Request: " + request.getRequestURL() + " raised " + accessDeniedException, accessDeniedException);

		request.setAttribute(WebAttributes.ACCESS_DENIED_403, accessDeniedException);
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				
		RequestDispatcher dispatcher = request.getRequestDispatcher("/access-denied");
		dispatcher.forward(request, response);
		
	}
	
}
