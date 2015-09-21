<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<%@ attribute name="url" required="true" type="java.lang.String" %>
<%@ attribute name="text" required="true" type="java.lang.String" %>
<%@ attribute name="domainObject" required="true" type="java.lang.Object" %>
<%@ attribute name="hasPermission" required="true" type="java.lang.String" rtexprvalue="false" %>

<security:authorize access="hasRole('ROLE_ADMIN')" var="adminAccess" />
<security:authorize access="hasRole('ROLE_EDITOR')">
	<security:accesscontrollist hasPermission="${hasPermission}" domainObject="${domainObject}" var="editorAccess" />
</security:authorize>
	
<c:if test="${adminAccess || editorAccess}">
	<a href="<c:url value='${url}' />">${text}</a>
</c:if>