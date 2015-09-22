<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>

<c:forEach var="article" items="${articles}">
	
	<article class="panel panel-primary">
		
		<div class="panel-heading">
			<div>
				<h3 class="panel-title"><a href="<c:url value='/article/show/${article.slug}' />">${article.title}</a></h3>
			</div>
			<div>
				<custom:articleLink url="/article/edit/${article.slug}" text="Edit" hasPermission="WRITE" domainObject="${article}" />
				<custom:articleLink url="/article/delete/${article.slug}" text="Delete" hasPermission="DELETE" domainObject="${article}" />
			</div>			
		</div>
		<div class="panel-body"><p>${article.content}</p></div>
		<div class="panel-footer">
			
			<div class="article-info">
				<b>${article.author.username}</b> | 
				<joda:format value="${article.createdAt}" style="MM" /> |
				
				<c:choose>
					<c:when test="${article.access eq 'PUB'}"><span class="text-success">Public</span></c:when>
					<c:otherwise><span class="text-danger">Private</span></c:otherwise>
				</c:choose>
			</div>
		
			<div class="article-comments">
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
			
		</div>
		
	</article>
</c:forEach>