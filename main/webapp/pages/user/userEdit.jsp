<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/orderCreation.css" />
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message  key="edit_profile_label"/></title>
</head>
<body>
<c:if test="${sessionScope.role == null || !sessionScope.role eq 'admin'}" >
    <c:redirect url="/pages/error403.jsp"/>
</c:if>
<h1><fmt:message  key="edit_profile_label"/></h1>
    <form action="/main" method="post">
        <input type="hidden" name="command" value="update_user"/>
        <label for="username"><fmt:message  key="name"/></label>
        <input type="text" name="u_name" value = "${user.name}"  required><br><br>

        <label for="surname"><fmt:message  key="surname"/></label>
        <input type="text" name="u_surname" value="${user.surname}" required><br><br>

        <label for="phone"><fmt:message  key="phone"/></label>
        <input type="text" name="u_phone_num" value="${user.phoneNumber}" required><br><br>

        <label for="email"><fmt:message  key="email"/></label>
        <input type="text" name="u_email" value="${user.email}" required><br><br>

        <label for="email"><fmt:message  key="login"/></label>
        <input type="text" name="u_login" value="${user.login}" required><br><br>

        <button type="submit"><fmt:message  key="save_changes"/></button>
    </form>
    <c:if test="${sessionScope.input_error != null}">
            <label class="error"><fmt:message  key="input_error"/></label>
    </c:if>
</body>
</html>