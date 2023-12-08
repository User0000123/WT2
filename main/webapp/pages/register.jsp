<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/register.css" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="register_label"/></title>
</head>
<body>
    <h1><fmt:message key="register_label"/></h1>
    <form action="/main" method="post">
        <input type="hidden" name="command" value="register">
        <label for="username"><fmt:message key="name"/></label>
        <input type="text" name="name" required><br><br>

        <label for="surname"><fmt:message key="surname"/></label>
        <input type="text" name="surname" required><br><br>

         <label for="phone"><fmt:message key="phone"/></label>
         <input type="text" name="phone" required><br><br>

         <label for="email"><fmt:message key="email"/></label>
         <input type="text" name="email" required><br><br>

         <label for="email"><fmt:message key="login"/></label>
         <input type="text" name="login" required><br><br>

        <label for="password"><fmt:message key="password"/></label>
        <input type="password" id="password" name="password" required><br><br>
        <input type="radio" name="role" value="client" checked><fmt:message key="average_user_label"/></input><br><br>
        <input type="radio" name="role" value="admin"><fmt:message key="admin_label"/></input><br><br>

        <button type="submit"><fmt:message key="register_button"/></button>
    </form>
    <c:if test="${sessionScope.input_error != null}">
        <label class="error"><fmt:message key="input_error"/></label>
    </c:if>
    <c:if test="${sessionScope.login_error != null}">
        <label class="error"><fmt:message key="login_error"/></label>
    </c:if>
    <c:if test="${sessionScope.email_error != null}">
        <label class="error"><fmt:message key="email_error"/></label>
    </c:if>
    <c:if test="${sessionScope.phone_error != null}">
        <label class="error"><fmt:message key="phone_error"/></label>
    </c:if>
</body>
</html>