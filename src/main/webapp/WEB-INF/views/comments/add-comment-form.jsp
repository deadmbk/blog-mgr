<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<section>
	<h3>Add comment</h3>

	<form:form method="POST" commandName="comment"
		action="${pageContext.request.contextPath}/article/${slug}/comment/add"
		class="form-horizontal">

		<div class="form-group">
			<label for="content" class="col-sm-2 control-label">Comment</label>
			<div class="col-sm-10">
				<form:textarea path="content" class="form-control" />
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-10 col-sm-offset-2">
				<input type="submit" value="Add comment" class="btn btn-primary" />
			</div>
		</div>

	</form:form>
</section>