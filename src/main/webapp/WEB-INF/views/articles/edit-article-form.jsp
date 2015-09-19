<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<script type="text/javascript">
$(document).ready(function() {
	
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

<h2>Edit form</h2>
<form:form method="POST" commandName="article" action="${pageContext.request.contextPath}/article/edit">
<form:hidden path="id" />
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
					
						<c:if test="${pageContext.request.userPrincipal.name != null && pageContext.request.userPrincipal.name != user.username}">
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
			</td>
		</tr>

<c:forEach var="user" items="${users}">
						${permittedUsers.contains(user.username)}
					</c:forEach>	

		<tr>
			<td><input type="submit" value="Edit" class="button" /></td>
			<td></td>
		</tr>
	</tbody>
</table>

</form:form>