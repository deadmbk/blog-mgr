package pl.edu.agh.blog.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.edu.agh.blog.models.Article;
import pl.edu.agh.blog.models.Comment;
import pl.edu.agh.blog.models.User;
import pl.edu.agh.blog.services.intf.ArticleService;
import pl.edu.agh.blog.services.intf.UserService;

@Controller
@RequestMapping(value="/article")
public class ArticlesController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(ArticlesController.class);
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		
		ModelAndView modelAndView = new ModelAndView("article-list");
		List<Article> articles = articleService.getArticles();
		
		modelAndView.addObject("articles", articles);
		return modelAndView;		
	}
	
	@RequestMapping(value = "/show/{slug}", method = RequestMethod.GET)
	public ModelAndView showArticle(@PathVariable String slug) {
		
		ModelAndView modelAndView = new ModelAndView("article-show");
		Article article = articleService.getArticleBySlug(slug);
		
		modelAndView.addObject("article", article);
		modelAndView.addObject("permittedUsers", articleService.getPermittedUsers(article));
		return modelAndView;		
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addArticlePage() {	
		
		ModelAndView modelAndView = new ModelAndView("article-add");		
		modelAndView.addObject("article", new Article());
		modelAndView.addObject("users", userService.getUsers());
		return modelAndView;		
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addingArticle(@ModelAttribute @Valid Article article, BindingResult result, 
			@RequestParam(value = "permittedUsers", required = false) List<String> usernames, final RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors()) {
			return new ModelAndView("article-add", "users", userService.getUsers());			
		}
					
		try {
			
			User author = getLoggedInUser();
			if (author == null) {
				throw new Exception("Session user not found.");
			}
			
			article.setAuthor(author);
			articleService.addArticle(article, getListOfUsersByUsernames(usernames));						
			redirectAttributes.addFlashAttribute("FLASH_SUCCESS", "The article has been successfully created.");
			return new ModelAndView("redirect:/article/list");
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
			redirectAttributes.addFlashAttribute("FLASH_ERROR", "The problem has occured during processing the request.");			
			return new ModelAndView("article-add", "users", userService.getUsers());		
		}
				
	}

	@RequestMapping(value = "/edit/{slug}", method = RequestMethod.GET)
	public ModelAndView editArticlePage(@PathVariable String slug) {	
		
		Article article = articleService.getArticleBySlug(slug);
		ModelAndView modelAndView = new ModelAndView("article-edit");
		modelAndView.addObject("article", article);
		modelAndView.addObject("users", userService.getUsers());
		modelAndView.addObject("permittedUsers", articleService.getPermittedUsers(article));
		return modelAndView;		
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView edittingArticle(@ModelAttribute @Valid Article article, BindingResult result, 
			@RequestParam(value = "permittedUsers", required = false) List<String> usernames, final RedirectAttributes redirectAttributes) {	
		
		if (result.hasErrors()) {								
			return new ModelAndView("article-edit", "users", userService.getUsers());			
		}
				
		try {
			
			articleService.updateArticle(article, getListOfUsersByUsernames(usernames));
			redirectAttributes.addFlashAttribute("FLASH_SUCCESS", "The article has been successfully edited.");
			return new ModelAndView("redirect:/article/list");
			
		} catch(Exception e) {
			
			e.printStackTrace();			
			redirectAttributes.addFlashAttribute("FLASH_ERROR", "The problem has occured during processing the request.");
			
			return new ModelAndView("article-edit", "users", userService.getUsers());						
		}
				
	}
	
	// Usuwanie jest wyj¹tkowe, poniewa¿ nie mamy id ani obiektu, tylko slug. Konieczne jest najpierw pobranie obiektu i dalsze przetwarzanie na podstawie id.
	// Nie mo¿na przes³aæ sluga a¿ do DAO, poniewa¿ serwis nie bêdzie mia³ mo¿liwoœci autoryzacji (na podstawie sluga nie okreœli praw dostêpu)
	@RequestMapping(value = "/delete/{slug}", method = RequestMethod.GET)
	public ModelAndView deleteArticle(@PathVariable String slug, final RedirectAttributes redirectAttributes) {
		Article article = articleService.getArticleBySlug(slug);
		articleService.deleteArticle(article.getId());
		redirectAttributes.addFlashAttribute("FLASH_SUCCESS", "The article has been successfully deleted.");	
		return new ModelAndView("redirect:/article/list");		
	}
	
	
	// Comments methods
	@RequestMapping(value = "/{slug}/comment/add", method = RequestMethod.GET)
	public ModelAndView addCommentPage(@PathVariable String slug) {
		ModelAndView modelAndView = new ModelAndView("comment-add");
		modelAndView.addObject("comment", new Comment());
		modelAndView.addObject("slug", slug);
		return modelAndView;
	}
	
	@RequestMapping(value = "/{slug}/comment/add", method = RequestMethod.POST)
	public ModelAndView addingComment(@ModelAttribute @Valid Comment comment, BindingResult result, 
			@PathVariable String slug, final RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors()) {
			return new ModelAndView("comment-add", "slug", slug);				
		}
		
		try {
			
			User author = getLoggedInUser();
			if (author == null) {
				throw new Exception("Session user not found.");
			}

			comment.setAuthor(author);
			articleService.addComment(slug, comment);
			redirectAttributes.addFlashAttribute("FLASH_SUCCESS", "The comment has been successfully added.");
			return new ModelAndView("redirect:/article/show/{slug}");
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
			redirectAttributes.addFlashAttribute("FLASH_ERROR", "The problem has occured during processing the request.");			
			return new ModelAndView("comment-add", "slug", slug);				
		}

	}
	
	@RequestMapping(value = "/{slug}/comment/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editCommentPage(@PathVariable String slug, @PathVariable int id) {
		Comment comment = articleService.getComment(id);
		ModelAndView modelAndView = new ModelAndView("comment-edit");
		modelAndView.addObject("comment", comment);
		modelAndView.addObject("slug", slug);
		return modelAndView;
	}
	
	@RequestMapping(value = "/{slug}/comment/edit", method = RequestMethod.POST)
	public ModelAndView edittingComment(@ModelAttribute @Valid Comment comment, BindingResult result, 
			@PathVariable String slug, final RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors()) {
			return new ModelAndView("comment-edit", "slug", slug);				
		}
		
		try {

			articleService.updateComment(comment);
			redirectAttributes.addFlashAttribute("FLASH_SUCCESS", "The comment has been successfully added.");
			return new ModelAndView("redirect:/article/show/{slug}");
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
			redirectAttributes.addFlashAttribute("FLASH_ERROR", "The problem has occured during processing the request.");			
			return new ModelAndView("comment-edit", "slug", slug);				
		}
	
	}
	
	@RequestMapping(value = "/{slug}/comment/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteComment(@PathVariable int id) {
		articleService.deleteComment(id);
		return new ModelAndView("redirect:/article/show/{slug}");
	}

	// --------------------------------------------------------------------------------------
	private List<User> getListOfUsersByUsernames(List<String> usernames) {
		
		List<User> users = new ArrayList<User>();
		if (usernames != null) {
			for (String username : usernames) {
				User user = userService.getUserByUsername(username);
				users.add(user);			
			}
		}
		
		return users;
	}
	
	private User getLoggedInUser() {		
		String authorName = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		return userService.getUserByUsername(authorName);
	}
}
