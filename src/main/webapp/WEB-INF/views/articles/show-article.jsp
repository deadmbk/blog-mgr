<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>

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
	
	<custom:articleLink url="/article/edit/${article.slug}" text="Edit article" hasPermission="WRITE" domainObject="${article}" />
	<custom:articleLink url="/article/delete/${article.slug}" text="Delete article" hasPermission="DELETE" domainObject="${article}" />

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
