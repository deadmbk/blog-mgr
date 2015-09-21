<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<div class="col-md-6">
	<h1>Blog application</h1>
</div>

<div class="col-md-6">	
	<ul>
	<c:choose>
		<c:when test="${pageContext.request.userPrincipal.name != null}">
		
			<form id="logoutForm" action="<c:url value='/logout' />" method="POST">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>
				
			<li> You are logged in as <b>${pageContext.request.userPrincipal.name}</b>.</li>
			<li><a id="logoutLink" href="<c:url value='/logout' />">Logout</a></li>	
			
		</c:when>
		<c:otherwise>
			<li><a href='<c:url value="/user/register" />' >Register</a></li>
			<li><a href='<c:url value="/login" />' >Sign in</a></li>
		</c:otherwise>
	</c:choose>
	</ul>
</div>