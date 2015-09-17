<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page session="false" %>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>

<c:choose>
	<c:when test="${pageContext.request.userPrincipal.name != null}">
		<p>
			Welcome <b>${pageContext.request.userPrincipal.name}</b>!
		</p>
	</c:when>
	<c:otherwise>
		<p>Welcome guest! No account yet? <a href='<c:url value="/user/register" />' >Register</a> or <a href='<c:url value="/login" />' >Sign in</a></p>
	</c:otherwise>
</c:choose>

<security:authorize access="isAuthenticated()">

</security:authorize>
