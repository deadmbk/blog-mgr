<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<h2>Edit form</h2>
<form:form method="POST" commandName="comment" action="${pageContext.request.contextPath}/article/${slug}/comment/edit">
<form:hidden path="id" />
<table>
	<tbody>
		<tr>
			<td>Content:</td>
			<td><form:textarea path="content" /></td>
		</tr>
		<tr>
			<td><input type="submit" value="Edit" class="button" /></td>
			<td></td>
		</tr>
	</tbody>
</table>

</form:form>