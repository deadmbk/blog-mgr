<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>

<script type="text/javascript">
$(document).ready(function() {
	
	var list = ${permittedUsers};
	$.each(list, function(index, element) {
		alert("dsad");
	});
	
	var popoverContent = function() {	
		
	}
	
	var options = {
		title : "Users allowed to see the article",
		trigger : "hover",
		placement : "bottom",
		content : ""
	};
	
});
</script>

<section>

	<div class="panel panel-primary">
		
		<div class="panel-heading">
			<div>
				<h3 class="panel-title">${article.title}</h3>
			</div>
			
			<div>
				<custom:articleLink url="/article/edit/${article.slug}" text="Edit" hasPermission="WRITE" domainObject="${article}" />
				<custom:articleLink url="/article/delete/${article.slug}" text="Delete" hasPermission="DELETE" domainObject="${article}" />
			</div>
		</div>
		
		<div class="panel-body">${article.content}</div>
		<div class="panel-footer">
		
			<div>
				<b>${article.author.username}</b> |
				<joda:format value="${article.createdAt}" style="MM" /> |
				<c:choose>
					<c:when test="${article.access eq 'PUB'}"><span class="text-success">Public</span></c:when>
					<c:otherwise><span class="text-danger">Private</span></c:otherwise>
				</c:choose>
			</div>
			
			<div>
				<security:authorize access="isAuthenticated()">
					<a href="<c:url value='/article/${article.slug}/comment/add' />">Add comment</a> |
				</security:authorize>
				Comments (${article.comments.size()})
			</div>		
		</div>	
	</div>	
	
	<security:authorize access="isAuthenticated()">
		<div id="comments">
			<h3>Comments</h3>
			<c:forEach var="comment" items="${article.comments}">
				
				<div class="panel panel-default">
					<div class="panel-body">${comment.content}</div>
					<div class="panel-footer">
					
						<div>
							<b>${comment.author.username}</b> |
							<joda:format value="${comment.createdAt}" style="MM" />
						</div>	
						
						<div>	
							<security:accesscontrollist hasPermission="READ,WRITE" domainObject="${comment}">
								<a href="<c:url value='/article/${article.slug}/comment/edit/${comment.id}' />">Edit</a>
							</security:accesscontrollist>
							
							<security:accesscontrollist hasPermission="READ,DELETE" domainObject="${comment}">
								<a href="<c:url value='/article/${article.slug}/comment/delete/${comment.id}' />">Delete</a>
							</security:accesscontrollist>
						</div>
						
					</div>
				</div>
			</c:forEach>
		</div>
	</security:authorize>

</section>
