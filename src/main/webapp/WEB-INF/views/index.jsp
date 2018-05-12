<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html ng-app="cinemaSeatingMapApp">
	<head>
		<title>Cinema seating map</title>
		<link rel="stylesheet" href="resources/lib/bootstrap/dist/css/bootstrap.css">
		<link rel="stylesheet" href="resources/css/main.css">
	</head>
	<body>
		<div ng-view></div>
		<script src="resources/lib/angular/angular.js"></script>
		<script src="resources/lib/angular-messages/angular-messages.js"></script>
		<script src="resources/lib/angular-route/angular-route.js"></script>
		<script src="resources/lib/angular-resource/angular-resource.js"></script>
		<script src="resources/js/application.js"></script>
	</body>
</html>