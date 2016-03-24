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
import org.springframework.web.servlet.view.RedirectView;

public class AbstractController {

	Logger log = Logger.getLogger(AbstractController.class);

	@ExceptionHandler(AccessDeniedException.class)
	public RedirectView handleError(HttpServletRequest request, HttpServletResponse response, Exception accessDeniedException) throws ServletException, IOException {
		
		log.error("Request: " + request.getRequestURL() + " raised " + accessDeniedException, accessDeniedException);

		request.setAttribute(WebAttributes.ACCESS_DENIED_403, accessDeniedException);
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		
		// if POST then redirect instead of forward - trying to forward POST request results in 405 error
		if ("POST".equals(request.getMethod())) {
			return new RedirectView("/access-denied", true);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/access-denied");
			dispatcher.forward(request, response);
		}

		return null;
	}
	
}
