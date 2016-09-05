package pl.edu.agh.blog.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

public class CustomUserDetailsContextMapper implements UserDetailsContextMapper, Serializable {

	private static final long serialVersionUID = 7374651304651181367L;

	@Override
	public UserDetails mapUserFromContext(DirContextOperations ctx, String username,
			Collection<? extends GrantedAuthority> authorities) {
		
		List<GrantedAuthority> mappedAuthorities = new ArrayList<GrantedAuthority>();
		for (GrantedAuthority authority : authorities) {			
			
			if (authority.getAuthority().equalsIgnoreCase("role_administrators")) {
				mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			} else if (authority.getAuthority().equalsIgnoreCase("role_editors")) {
				mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_EDITOR"));
			} else {
				// users
				mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
			}
		}
		
		return new User(username, "", true, true, true, true, mappedAuthorities);
	}

	@Override
	public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
	}

}
