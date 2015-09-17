<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<script type="text/javascript">
	$(document).ready(function() {		
		$('#logoutLink').click(function(event) {
			$('#logoutForm').submit();
			return false;
		});
	});
</script>

<h1>Navigation</h1>
<ul>
	<li><a href="<c:url value='/' />">Home page</a></li>
	
	<security:authorize access="hasRole('ROLE_ADMIN')">
		<li><a href="<c:url value='/user/list' />">User list</a></li>
	</security:authorize>
	
	<c:choose>
		<c:when test="${pageContext.request.userPrincipal.name != null}">
		
			<form id="logoutForm" action="<c:url value='/logout' />" method="POST">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>
		
			<li><a id="logoutLink" href="<c:url value='/logout' />">Logout</a></li>
		</c:when>
		<c:otherwise>
			<li><a href='<c:url value="/user/register" />' >Register</a> or <a href='<c:url value="/login" />' >Sign in</a></li>
		</c:otherwise>
	</c:choose>
	
	<li><a href="<c:url value='/article/list' />">Articles</a></li>
	<security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')">
		<li><a href="<c:url value='/article/add' />">Add article</a></li>
	</security:authorize>
	
</ul>