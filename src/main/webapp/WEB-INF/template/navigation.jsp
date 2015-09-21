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
<ul class="nav nav-pills nav-stacked">
	<li><a href="<c:url value='/' />">Home page</a></li>
	
	<security:authorize access="hasRole('ROLE_ADMIN')">
		<li><a href="<c:url value='/user/list' />">User list</a></li>
	</security:authorize>
	
	<li><a href="<c:url value='/article/list' />">Articles</a></li>
	<security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')">
		<li><a href="<c:url value='/article/add' />">Add article</a></li>
	</security:authorize>
	
	
		
</ul>