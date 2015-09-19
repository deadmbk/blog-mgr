<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<script type="text/javascript">
$(document).ready(function() {
	
	$('input[value="PUB"]').prop("checked", true);
	$('tr.permittedUsers').hide();
	
	$('input[type="radio"]').click(function() {
		if ( $(this).val() == 'PRV' ) {
			$('tr.permittedUsers').show();
		} else {
			$('tr.permittedUsers').hide();
		}
	});
	
});
</script>

<h2>Add article</h2>

<form:form method="POST" commandName="article" action="${pageContext.request.contextPath}/article/add">
<table>
	<tbody>
		<tr>
			<td>Title:</td>
			<td><form:input path="title" /></td>
		</tr>
		<tr>
			<td>Content:</td>
			<td><form:textarea path="content" /></td>
		</tr>
		<tr>
			<td>Access type:</td>
			<td>
				<form:radiobutton path="access" value="PUB" />Public
				<form:radiobutton path="access" value="PRV" />Private
			</td>
		</tr>
		<tr class="permittedUsers">
			<td>Users:</td>
			<td>
				<select name="permittedUsers" multiple="multiple">
					<c:forEach var="user" items="${users}">
						<option value="${user.username}">${user.username}</option>
					</c:forEach>					
				</select>
			</td>
		</tr>
		<tr>
			<td><input type="submit" value="Add article" class="button" /></td>
			<td></td>
		</tr>
	</tbody>
</table>

</form:form>