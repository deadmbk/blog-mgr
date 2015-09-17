<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<h2>Articles</h2>

<c:forEach var="article" items="${articles}">
	<div class="article-title">
		<h2>
			<a href="<c:url value='/article/show/${article.slug}' />">${article.title}</a>
		</h2>
	</div>
	<div class="article-content">${article.content}</div>
	<div class="article-extras">
		Author: ${article.author.username} 
		Created: <joda:format value="${article.createdAt}" style="MM" />
		Access: 
		<c:choose>
			<c:when test="${article.access eq 'PUB'}">Public</c:when>
			<c:otherwise>Private</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test="${pageContext.request.userPrincipal.name != null}">
				<a href="<c:url value='/article/show/${article.slug}#comments' />">Comments (${article.comments.size()})</a> |
				<a href="<c:url value='/article/${article.slug}/comment/add' />">Add comment</a>
			</c:when>
			<c:otherwise>
				<a href="<c:url value='/login' />">Log in</a> to see and add comments
			</c:otherwise>
		</c:choose>
	</div>
</c:forEach>