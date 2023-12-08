<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/error403.css" />
<html>
<head>
    <title><fmt:message key="forbidden_header"/>а</title>
    <style>

    </style>
</head>
<body>
    <div class="error-container">
        <h1><fmt:message key="forbidden_label"/></h1>
        <p><fmt:message key="forbidden_text"/></p>
    </div>
</body>
</html>