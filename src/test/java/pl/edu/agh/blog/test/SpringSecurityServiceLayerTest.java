package pl.edu.agh.blog.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import pl.edu.agh.blog.config.ApplicationContextConfig;
import pl.edu.agh.blog.models.Article;
import pl.edu.agh.blog.services.intf.UserService;
import pl.edu.agh.blog.test.mock.service.MockArticleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationContextConfig.class})
@WebAppConfiguration
public class SpringSecurityServiceLayerTest {
	
	@Autowired
	UserService userService;
	
	@Autowired
	MockArticleService mockArticleService;
	
	private Article privateArticle;
	private Article publicArticle;
	private Article thirdArticle;
	
	@Before
	public void setUp() throws Exception {		
		privateArticle = mockArticleService.getArticleBySlugForTest("the-continuation-3-2");
		publicArticle = mockArticleService.getArticleBySlugForTest("lorem-ipsum");
		thirdArticle = mockArticleService.getArticleBySlugForTest("test");		
	}

	// Public article test
	@Test
	@WithAnonymousUser
	public void testGetPublicArticleAsAnonymousUser() throws Exception {		
		mockArticleService.getArticleBySlug(publicArticle.getSlug());
	}
	
	@Test
	@WithMockUser
	public void testGetPublicArticleAsUser() throws Exception {		
		mockArticleService.getArticleBySlug(publicArticle.getSlug());
	}
	
	// Private article test
	@Test(expected = AccessDeniedException.class)
	@WithMockUser
	public void testGetPrivateArticleAsUser() throws Exception {		
		mockArticleService.getArticleBySlug(privateArticle.getSlug());
	}
	
	@Test
	@WithMockUser("mbk")
	public void testGetPrivateArticleAsAuthor() throws Exception {		
		mockArticleService.getArticleBySlug(privateArticle.getSlug());
	}
	
	@Test
	@WithMockUser("test")
	public void testGetPrivateArticleAsPermittedUser() throws Exception {		
		mockArticleService.getArticleBySlug(privateArticle.getSlug());
	}
	
	// Adding article test
	@Test(expected=AccessDeniedException.class)
	@WithMockUser(roles={"USER"})
	public void testAddArticleWithRoleUser() throws Exception {
		mockArticleService.addArticle(new Article());
	}

	@Test
	@WithMockUser(roles={"ADMIN"})
	public void testAddArticleWithRoleAdmin() throws Exception {
		mockArticleService.addArticle(new Article());
	}
	
	@Test
	@WithMockUser(roles={"EDITOR"})
	public void testAddArticleWithRoleEditor() throws Exception {
		mockArticleService.addArticle(new Article());
	}
	
	// Update article test
	@Test
	@WithMockUser(roles={"ADMIN"})
	public void testUpdateArticleAsAdmin() throws Exception {
		mockArticleService.updateArticle(thirdArticle);
	}
	
	@Test(expected=AccessDeniedException.class)
	@WithMockUser(username="mbk", roles={"EDITOR"})
	public void testUpdateArticleAsEditor() throws Exception {
		mockArticleService.updateArticle(publicArticle);
	}
	
	@Test
	@WithMockUser(username="mbk", roles={"EDITOR"})
	public void testUpdateArticleAsAuthor() throws Exception {
		mockArticleService.updateArticle(privateArticle);
	}
	
	@Test(expected=AccessDeniedException.class)
	@WithMockUser(roles={"USER"})
	public void testUpdateArticleAsUser() throws Exception {
		mockArticleService.updateArticle(publicArticle);
	}
	
	// Delete article test
	@Test
	@WithMockUser(roles={"ADMIN"})
	public void testDeleteArticleAsAdmin() throws Exception {
		mockArticleService.deleteArticle(thirdArticle.getId());
	}
	
	@Test(expected=AccessDeniedException.class)
	@WithMockUser(username="mbk", roles={"EDITOR"})
	public void testDeleteArticleAsEditor() throws Exception {
		mockArticleService.deleteArticle(thirdArticle.getId());
	}
	
	@Test
	@WithMockUser(username="mbk", roles={"EDITOR"})
	public void testDeleteArticleAsAuthor() throws Exception {
		mockArticleService.deleteArticle(privateArticle.getId());
	}
	
	@Test(expected=AccessDeniedException.class)
	@WithMockUser(roles={"USER"})
	public void testDeleteArticleAsUser() throws Exception {
		mockArticleService.deleteArticle(thirdArticle.getId());
	}
	
	// Add comment test
	@Test
	@WithMockUser
	public void testAddCommentAsUser() throws Exception {
		mockArticleService.addComment();
	}
	
	@Test(expected=AccessDeniedException.class)
	@WithAnonymousUser
	public void testAddCommentAsAnonymousUser() throws Exception {
		mockArticleService.addComment();
	}
	
	// Update comment test
	@Test
	@WithMockUser(roles={"ADMIN"})
	public void testUpdateCommentAsAdmin() throws Exception {
		mockArticleService.updateComment(publicArticle.getComments().iterator().next());
	}
	
	@Test
	@WithMockUser(username="test")
	public void testUpdateCommentAsAuthor() throws Exception {
		mockArticleService.updateComment(publicArticle.getComments().iterator().next());
	}
	
	@Test(expected=AccessDeniedException.class)
	@WithMockUser
	public void testUpdateCommentAsUser() throws Exception {
		mockArticleService.updateComment(publicArticle.getComments().iterator().next());
	}
	
	// Delete comment test
	@Test
	@WithMockUser(roles={"ADMIN"})
	public void testDeleteCommentAsAdmin() throws Exception {
		mockArticleService.deleteComment(publicArticle.getComments().iterator().next().getId());
	}
	
	@Test
	@WithMockUser(username="test")
	public void testDeleteCommentAsAuthor() throws Exception {
		mockArticleService.deleteComment(publicArticle.getComments().iterator().next().getId());
	}
	
	@Test(expected=AccessDeniedException.class)
	@WithMockUser
	public void testDeleteCommentAsUser() throws Exception {
		mockArticleService.deleteComment(publicArticle.getComments().iterator().next().getId());
	}
}
