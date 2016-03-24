<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<section>
	<h2>Edit article</h2>
	
	<security:authentication var="session_user" property="principal" />
	<form:form method="POST" commandName="article" action="${pageContext.request.contextPath}/article/edit" class="form-horizontal">
		
		<form:hidden path="id" />

		<div class="form-group">
			<label for="title" class="col-sm-2 control-label">Title</label>
			<div class="col-sm-10">
				<form:input path="title" class="form-control" placeholder="Title" />
			</div>
		</div>

		<div class="form-group">
			<label for="content" class="col-sm-2 control-label">Content</label>
			<div class="col-sm-10">
				<form:textarea path="content" class="form-control" rows="6" />
			</div>
		</div>

		<div class="form-group">
			<label class="col-sm-2 control-label">Access type</label>
			<div class="col-sm-10">
				<label class="radio-inline"> <form:radiobutton path="access"
						value="PUB" /> Public
				</label> <label class="radio-inline"> <form:radiobutton
						path="access" value="PRV" /> Private
				</label>
			</div>
		</div>

		<div class="form-group">
			<label class="col-sm-2 control-label permitted-users">Users</label>
			<div class="col-sm-10 permitted-users">
				<select name="permittedUsers" multiple="multiple"
					class="form-control" size="5">
					<c:forEach var="user" items="${users}">
					<%-- ${pageContext.request.userPrincipal.name != null && pageContext.request.userPrincipal.name != user.username} --%>
						<c:if test="${session_user.username != null && session_user.username != user.username}">
							<c:choose>
								<c:when test="${permittedUsers.contains(user.username)}">
									<option value="${user.username}" selected="selected">${user.username}</option>
								</c:when>
								<c:otherwise>
									<option value="${user.username}">${user.username}</option>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-10 col-sm-offset-2">
				<input type="submit" value="Edit article" class="btn btn-primary" />
			</div>
		</div>

	</form:form>
</section>