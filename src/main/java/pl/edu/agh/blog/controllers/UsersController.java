package pl.edu.agh.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.edu.agh.blog.models.Role;
import pl.edu.agh.blog.models.User;
import pl.edu.agh.blog.services.intf.RoleService;
import pl.edu.agh.blog.services.intf.UserService;

@Controller
@RequestMapping(value="/user")
public class UsersController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		
		ModelAndView modelAndView = new ModelAndView("user-list");
		List<User> users = userService.getUsersWithRole();
		
		modelAndView.addObject("users", users);
		return modelAndView;		
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView registerPage() {
		
		ModelAndView modelAndView = new ModelAndView("register");		
		modelAndView.addObject("user", new User());
		
		return modelAndView;
		
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public ModelAndView registeringUser(@ModelAttribute @Valid User user, BindingResult result, final RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors()) {								
			return new ModelAndView("register");			
		}
		
		Role role = roleService.getRoleByName("ROLE_USER");
		user.setRole(role);
		
		userService.addUser(user);
		redirectAttributes.addFlashAttribute("FLASH_SUCCESS", "You have been successfully registered.");
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public ModelAndView editUserPage(@PathVariable int id) {
		
		ModelAndView modelAndView = new ModelAndView("user-edit");
		User user = userService.getUser(id);
		
		modelAndView.addObject("user", user);
		modelAndView.addObject("roles", roleService.getRoles());
		return modelAndView;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public ModelAndView edittingUser(@ModelAttribute @Valid User user, BindingResult result, final RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors()) {								
			return new ModelAndView("user-edit", "roles", roleService.getRoles());			
		}
		
		userService.updateUser(user);
		redirectAttributes.addFlashAttribute("FLASH_SUCCESS", "User (id=" + user.getId() + ") has been successfully edited.");
		return new ModelAndView("redirect:/user/list");
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public ModelAndView deleteUser(@PathVariable int id, final RedirectAttributes redirectAttributes) {
		userService.deleteUser(id);
		redirectAttributes.addFlashAttribute("FLASH_SUCCESS", "User (id=" + id + ") has been successfully deleted.");
		return new ModelAndView("redirect:/user/list");
	}
	
}
