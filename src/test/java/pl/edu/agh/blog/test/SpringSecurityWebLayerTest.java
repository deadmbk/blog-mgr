package pl.edu.agh.blog.test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import pl.edu.agh.blog.config.ApplicationContextConfig;
import pl.edu.agh.blog.services.intf.ArticleService;
import pl.edu.agh.blog.services.intf.UserService;
import pl.edu.agh.blog.test.mock.service.MockArticleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationContextConfig.class})
@WebAppConfiguration
public class SpringSecurityWebLayerTest {
	
	@Autowired
	private WebApplicationContext context;
	private MockMvc mvc;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ArticleService articleService;
	
	@Autowired
	MockArticleService mockArticleService;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}
	
	@Test
	public void testAuthentication() throws Exception {
		mvc.perform(formLogin("/login").user("admin").password("admin")).andExpect(authenticated());
	}
	
	@Test
	public void testLogout() throws Exception {
		mvc.perform(logout());
	}

	@Test
	public void testUserListWithAdminRole() throws Exception {
		mvc.perform(get("/user/list").with(user("user").roles("ADMIN"))).andExpect(status().isOk());
	}
	
	@Test
	public void testUserListWithEditorRole() throws Exception {
		mvc.perform(get("/user/list").with(user("user").roles("EDITOR"))).andExpect(status().isForbidden());
	}
	
	@Test
	public void testUserListWithUserRole() throws Exception {
		mvc.perform(get("/user/list").with(user("user").roles("USER"))).andExpect(status().isForbidden());
	}

	@Test
	public void testArticleAddEditor() throws Exception {
		mvc.perform(get("/article/add").with(user("user").roles("EDITOR"))).andExpect(status().isOk());
	}
	
	@Test
	public void testArticleAddAdmin() throws Exception {
		mvc.perform(get("/article/add").with(user("user").roles("ADMIN"))).andExpect(status().isOk());
	}
	
	@Test
	public void testArticleAddUser() throws Exception {
		mvc.perform(get("/article/add").with(user("user").roles("USER"))).andExpect(status().isForbidden());
	}
	
	@Test
	public void testRegister() throws Exception {
		mvc.perform(get("/register").with(user("user"))).andExpect(status().isForbidden());
	}

}
