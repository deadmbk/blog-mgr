package pl.edu.agh.blog.services.impl;

import java.util.ListIterator;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PermissionFactory;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import pl.edu.agh.blog.services.intf.AbstractService;

public abstract class AbstractServiceImpl implements AbstractService {
	
	@Autowired
	private MutableAclService mutableAclService;
	
	@Autowired
	private PermissionFactory permissionFactory;
	
	@Transactional
	public void createAcl(Class<?> clazz, long id, String principal, Permission permissions) {

		ObjectIdentity oid = new ObjectIdentityImpl(clazz, id);
		MutableAcl acl;

		try {
			acl = (MutableAcl) mutableAclService.readAclById(oid);
		} catch (NotFoundException e) {
			acl = mutableAclService.createAcl(oid);
		}

		acl.insertAce(acl.getEntries().size(), permissions, new PrincipalSid(principal), true);
		mutableAclService.updateAcl(acl);
	}

	protected void createAcl(Class<?> clazz, long id) {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		createAcl(clazz, id, user.getUsername(), permissionFactory.buildFromMask(31) );

	}
	
	@Override
	public void updateAcl(String type, long id, String principal, Permission newPermission) throws ClassNotFoundException {

		Class<?> clazz = Class.forName(type);
		ObjectIdentity oid = new ObjectIdentityImpl(clazz, id);

		MutableAcl acl = (MutableAcl) mutableAclService.readAclById(oid);

		int i = 0;
		for (AccessControlEntry ace : acl.getEntries()) {

			PrincipalSid sid = (PrincipalSid) ace.getSid();
			if (sid.getPrincipal().equals(principal)) {

				acl.updateAce(i, newPermission);
				mutableAclService.updateAcl(acl);
				break;

			}

			++i;
		}

	}
	
	protected void deleteAcl(Class<?> clazz, long id) {

		ObjectIdentity oid = new ObjectIdentityImpl(clazz, id);
		mutableAclService.deleteAcl(oid, true);

	}

	@Override
	public void deleteAcl(String type, long id, String principal) throws ClassNotFoundException {

		Class<?> clazz = Class.forName(type);
		ObjectIdentity oid = new ObjectIdentityImpl(clazz, id);

		MutableAcl acl = (MutableAcl) mutableAclService.readAclById(oid);

		int i = 0;
		ListIterator<AccessControlEntry> it = acl.getEntries().listIterator();
		while (it.hasNext()) {

			AccessControlEntry ace = it.next();
			PrincipalSid sid = (PrincipalSid) ace.getSid();
			if (sid.getPrincipal().equals(principal)) {

				acl.deleteAce(i);
				mutableAclService.updateAcl(acl);
				break;

			}

			++i;
		}

	}
}
