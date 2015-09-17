<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<h2>Add comment</h2>

<form:form method="POST" commandName="comment" action="${pageContext.request.contextPath}/article/${slug}/comment/add">
<table>
	<tbody>
		<tr>
			<td>Content:</td>
			<td><form:textarea path="content" /></td>
		</tr>
		<tr>
			<td><input type="submit" value="Add comment" class="button" /></td>
		</tr>
	</tbody>
</table>

</form:form>