<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>List of users</h2>
<table class="table table-striped">
	<thead>
		<tr>
			<th>Id</th>
			<th>Username</th>
			<th>Role</th>
			<th>Actions</th>
		</tr>
	</thead>
	<tbody>
	
		<c:forEach var="user" items="${users}">
			<tr>
				<td>${user.id}</td>
				<td>${user.username}</td>
				<td>${user.role.name}</td>
				<td>
					<a href="<c:url value='/user/edit/${user.id}' />">Edit</a>
					<a href="<c:url value='/user/delete/${user.id}' />">Delete</a>
				</td>
			</tr>		
		</c:forEach>
		
	</tbody>
</table>