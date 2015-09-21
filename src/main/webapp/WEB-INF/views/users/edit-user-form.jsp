<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<h2>Edit user</h2>
<form:form method="POST" commandName="user"
	action="${pageContext.request.contextPath}/user/edit"
	class="form-horizontal">
	<form:hidden path="id" />
	<div class="form-group">
		<label for="username" class="col-sm-2 control-label">Username</label>
		<div class="col-sm-10">
			<form:input path="username" class="form-control"
				placeholder="Username" />
		</div>
	</div>

	<div class="form-group">
		<label for="password" class="col-sm-2 control-label">Password</label>
		<div class="col-sm-10">
			<form:password path="password" class="form-control"
				placeholder="Password" />
		</div>
	</div>

	<div class="form-group">
		<label class="col-sm-2 control-label">Role</label>
		<div class="col-sm-10 permitted-users">
			<form:select path="role.id" class="form-control">
				<c:forEach var="role" items="${roles}">
					<form:option value="${role.id}">${role.name}</form:option>
				</c:forEach>
			</form:select>
		</div>
	</div>

	<div class="form-group">
		<div class="col-sm-2"></div>
		<div class="col-sm-10">
			<input type="submit" value="Register" class="btn btn-primary" />
		</div>
	</div>

</form:form>