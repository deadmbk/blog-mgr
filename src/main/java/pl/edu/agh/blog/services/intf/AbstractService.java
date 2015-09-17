package pl.edu.agh.blog.services.intf;

import org.springframework.security.acls.model.Permission;

public interface AbstractService {

	public void createAcl(Class<?> clazz, long id, String principal, Permission permissions);
	public void updateAcl(String type, long id, String principal, Permission newPermission) throws ClassNotFoundException;
	public void deleteAcl(String type, long id, String principal) throws ClassNotFoundException;

}