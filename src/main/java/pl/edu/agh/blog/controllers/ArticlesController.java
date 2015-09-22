package pl.edu.agh.blog.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
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
	public ModelAndView addingArticle(@ModelAttribute Article article, @RequestParam(value = "permittedUsers", required = false) String [] permittedUsers, final RedirectAttributes redirectAttributes) {
		
		String authorName = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		User author = userService.getUserByUsername(authorName);

		// TODO exceptions
		if (author != null) {
			
			article.setAuthor(author);
			articleService.addArticle(article, permittedUsers);						
			redirectAttributes.addFlashAttribute("FLASH_SUCCESS", "The article has been successfully created.");
			
		} else {			
			redirectAttributes.addFlashAttribute("FLASH_ERROR", "The problem has occured during processing the request.");			
		}
	
		return new ModelAndView("redirect:/article/list");		
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
	public ModelAndView edittingArticle(@ModelAttribute Article article, @RequestParam(value = "permittedUsers", required = false) String [] permittedUsers, final RedirectAttributes redirectAttributes) {	
		articleService.updateArticle(article, permittedUsers);
		redirectAttributes.addFlashAttribute("FLASH_SUCCESS", "The article has been successfully edited.");
		return new ModelAndView("redirect:/article/list");		
	}
	
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
	public ModelAndView addingComment(@ModelAttribute Comment comment, @PathVariable String slug) {
		
		String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		User author = userService.getUserByUsername(username);
		
		comment.setAuthor(author);
		
		articleService.addComment(slug, comment);
		return new ModelAndView("redirect:/article/show/{slug}");
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
	public ModelAndView edittingComment(@ModelAttribute Comment comment, @PathVariable String slug) {
		articleService.updateComment(comment);
		return new ModelAndView("redirect:/article/show/{slug}");
	}
	
	@RequestMapping(value = "/{slug}/comment/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteComment(@PathVariable int id) {
		articleService.deleteComment(id);
		return new ModelAndView("redirect:/article/show/{slug}");
	}

}
