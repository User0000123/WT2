<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="login_label"/></title>
</head>
<body>
     <h1><fmt:message key="login_label"/></h1>
     <form action="/main" method="post">
        <input type="hidden" name="command" value="login">
        <label><fmt:message key="login"/></label>
        <input type="text" id="login" name="login" required><br><br>
        <label><fmt:message key="password"/></label>
        <input type="password" id="password" name="password" required><br><br>
        <button type="submit"><fmt:message key="login_label"/></button>
     </form>
     <c:if test="${sessionScope.input_error != null}">
        <label class="error"><fmt:message key="input_error"/></label>
     </c:if>
     <c:if test="${sessionScope.auth_error != null}">
         <label class="error"><fmt:message key="auth_error"/></label>
     </c:if>
   </body>
</html>