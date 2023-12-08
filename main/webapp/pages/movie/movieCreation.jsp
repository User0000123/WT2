<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<meta charset="UTF-8">
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<html>
<head>
    <title>Создание курса по программированию</title>
</head>
<body>
    <c:if test="${sessionScope.role == null || !sessionScope.role eq 'admin'}" >
        <c:redirect url="/pages/error403.jsp"/>
    </c:if>
    <h1><fmt:message key="course_creation_label"/></h1>
    <form action="/main" method="post" accept-charset="UTF-8" >
        <input type="hidden" name="command" value="add_movie"/>
        <label for="title"><fmt:message key="movie_name"/></label><br>
        <input type="text" name="m_name"  required><br>

        <label for="director"><fmt:message key="director"/></label><br>
        <input type="text" name="m_director"  required><br>

        <label for="genre"><fmt:message key="genre"/></label><br>
        <input type="text" name="m_genre"  required><br>

        <label for="price"><fmt:message key="price"/></label><br>
        <input type="number" name="m_price" min="0" required><br>

        <label for="description"><fmt:message key="description"/></label><br>
        <textarea name="m_description" rows="4" cols="50"></textarea><br>

        <button type="submit"><fmt:message key="create_movie"/></button>
    </form>
    <c:if test="${sessionScope.input_error != null}">
        <label class="error"><fmt:message key="input_error"/></label>
    </c:if>
</body>
</html>