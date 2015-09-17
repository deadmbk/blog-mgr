<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<h2>Edit form</h2>
<form:form method="POST" commandName="user" action="${pageContext.request.contextPath}/user/edit">
<form:hidden path="id" />
<table>
	<tbody>
		<tr>
			<td>Username:</td>
			<td><form:input path="username" /></td>
		</tr>
		<tr>
			<td>Password:</td>
			<td><form:password path="password" /></td>
		</tr>

		<tr>
			<td>Role:</td>
			<td>
				<form:select path="role.id">
					<c:forEach var="role" items="${roles}">
						<form:option value="${role.id}">${role.name}</form:option>
					</c:forEach>
				</form:select>
			</td>
		</tr>

		<tr>
			<td><input type="submit" value="Register" class="button" /></td>
			<td></td>
		</tr>
	</tbody>
</table>

</form:form>