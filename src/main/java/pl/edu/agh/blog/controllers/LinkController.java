package pl.edu.agh.blog.controllers;

import java.security.Principal;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LinkController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(LinkController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(@RequestParam(value = "logout", required = false) String logout, Locale locale) {
		
		ModelAndView modelAndView = new ModelAndView("default");
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		modelAndView.addObject("serverTime", formattedDate );
		
		if (logout != null) {
			modelAndView.addObject("FLASH_INFO", "You've been successfully logged out.");
		  }
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/access-denied", method = RequestMethod.GET)
	public ModelAndView accessDenied(Principal user) {		
		
		ModelAndView model = new ModelAndView();
		model.addObject("message", "You do not have permission to access this page!");

		model.setViewName("access-denied");
		return model;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error) {

	  ModelAndView model = new ModelAndView("login");
	  
	  if (error != null) {
		model.addObject("error", "Invalid username and password!");
	  }

	  return model;

	}
	
}
