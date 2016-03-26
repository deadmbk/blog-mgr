<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<section>
	<h2>Register</h2>
	<form:form method="POST" commandName="user"
		action="${pageContext.request.contextPath}/user/register" class="form-horizontal">

		<div class="form-group">
			<label for="username" class="col-sm-2 control-label">Username</label>
			<div class="col-sm-10">
				<form:input path="username" class="form-control" placeholder="Username" />
			</div>
			<form:errors path="username" />
		</div>

		<div class="form-group">
			<label for="password" class="col-sm-2 control-label">Password</label>
			<div class="col-sm-10">
				<form:password path="password" class="form-control" placeholder="Password" />
			</div>
			<form:errors path="password" />
		</div>

		<div class="form-group">
			<div class="col-sm-10 col-sm-offset-2">
				<input type="submit" value="Register" class="btn btn-primary" />
				<a href="${pageContext.request.contextPath}/">Cancel</a>
			</div>
		</div>
		
	</form:form>
</section>