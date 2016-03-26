<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<section>

	<h3>Edit comment</h3>
	<form:form method="POST" commandName="comment"
		action="${pageContext.request.contextPath}/article/${slug}/comment/edit"
		class="form-horizontal">

		<form:hidden path="id" />

		<div class="form-group">
			<label for="content" class="col-sm-2 control-label">Comment</label>
			<div class="col-sm-10">
				<form:textarea path="content" class="form-control" />
			</div>
			<form:errors path="content" />
		</div>

		<div class="form-group">
			<div class="col-sm-10 col-sm-offset-2">
				<input type="submit" value="Edit comment" class="btn btn-primary" />
				<a href="${pageContext.request.contextPath}/article/show/${slug}">Cancel</a>
			</div>
		</div>

	</form:form>
	
</section>