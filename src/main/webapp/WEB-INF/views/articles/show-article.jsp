<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<h2>${article.title}</h2>

<div class="article-content">${article.content}</div>
<div class="article-extras">
	Author: ${article.author.username} 
	Created: <joda:format value="${article.createdAt}" style="MM" />
	Access: 
	<c:choose>
		<c:when test="${article.access eq 'PUB'}">Public</c:when>
		<c:otherwise>Private</c:otherwise>
	</c:choose>
	
	<security:authorize access="hasRole('ROLE_ADMIN')" var="adminAccess" />
	<security:authorize access="hasRole('ROLE_EDITOR')">
		<security:accesscontrollist hasPermission="WRITE" domainObject="${article}" var="editorEditAccess" />
		<security:accesscontrollist hasPermission="DELETE" domainObject="${article}" var="editorDeleteAccess" />
	</security:authorize>
	
	<c:if test="${adminAccess || editorEditAccess}">
		<a href="<c:url value='/article/edit/${article.slug}' />">Edit article</a>
	</c:if>
	
	<c:if test="${adminAccess || editorDeleteAccess}">
		<a href="<c:url value='/article/delete/${article.slug}' />">Delete article</a>
	</c:if>

	Comments (${article.comments.size()})

	<security:authorize access="isAuthenticated()">
		
		<a href="<c:url value='/article/${article.slug}/comment/add' />">Add comment</a>
		<c:forEach var="comment" items="${article.comments}">
			<div class="comment">
				<div class="comment-content">${comment.content}</div>
				<div class="comment-extras">
					Author: ${comment.author.username} 
					Created: <joda:format value="${comment.createdAt}" style="MM" />
					
					<security:accesscontrollist hasPermission="READ,WRITE" domainObject="${comment}">
						<a href="<c:url value='/article/${article.slug}/comment/edit/${comment.id}' />">Edit comment</a>
					</security:accesscontrollist>
					
					<security:accesscontrollist hasPermission="READ,DELETE" domainObject="${comment}">
						<a href="<c:url value='/article/${article.slug}/comment/delete/${comment.id}' />">Delete comment</a>
					</security:accesscontrollist>
				</div>
			</div>
		</c:forEach>
		
	</security:authorize>
</div>
