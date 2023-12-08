<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css" />
<html>
<head>
    <title><fmt:message key="internal_header"/></title>
</head>
<body>
    <div class="error-container">
        <h1><fmt:message key="internal_label"/></h1>
        <p><fmt:message key="internal_text"/></p>
    </div>
</body>
</html>