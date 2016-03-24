<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Blog - <tiles:getAsString name="title" /></title>	
	<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/flash.css" />">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css" />">
	
	<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
	<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
	<script src="<c:url value="/resources/js/script.js" />"></script>
</head>
<body>

<div class="container">

	<p>
		<c:if test="${FLASH_SUCCESS != null}">
			<div class="flash-message flash-success">
				<div class="flash-text">${FLASH_SUCCESS}</div>
				<div class="flash-close">x</div>
			</div>
		</c:if>
	
		<c:if test="${FLASH_INFO != null}">
			<div class="flash-message flash-info">
				<div class="flash-text">${FLASH_INFO}</div>
				<div class="flash-close">x</div>
			</div>
		</c:if>
		
		<c:if test="${FLASH_ERROR != null}">
			<div class="flash-message flash-error">
				<div class="flash-text">${FLASH_ERROR}</div>
				<div class="flash-close">x</div>
			</div>
		</c:if>
	</p>
	
	<header class="row page-header">
		<tiles:insertAttribute name="header" />
	</header>
	
	<div class="row">
		
		<nav class="col-md-2 col-md-offset-1">
			<tiles:insertAttribute name="navigation" />
		</nav>
		
		<section class="col-md-8" id="content">
			<h2>Main content</h2>
			<tiles:insertAttribute name="content" />
		</section>
	</div>
	
	
	<footer>
		<tiles:insertAttribute name="footer" />
	</footer>
	
</div>

</body>
</html>