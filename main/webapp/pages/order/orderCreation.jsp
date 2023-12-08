<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/orderCreation.css" />
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="order_finalization_label"/></title>
</head>
<body>
    <c:if test="${sessionScope.role == null}" >
        <c:redirect url="/pages/error403.jsp"/>
    </c:if>
    <h1><fmt:message key="order_finalization_label"/></h1>
    <h2><fmt:message key="movies_in_cart"/></h2>
    <c:if test="${sessionScope.chosen != null}">
    <table>
        <tr>
            <th><fmt:message key="movie_name"/></th>
            <th><fmt:message key="main_tech"/></th>
            <th><fmt:message key="price"/></th>
            <c:forEach var="movie" items="${sessionScope.chosen}">
                <tr>
                    <td>${movie.name}</td>
                    <td>${movie.mainTech}</td>
                    <td>${movie.price}</td>
                </tr>
            </c:forEach>
        </tr>
    </table>
     <form action="/main" method="post">
        <input type="hidden" name="command" value="create_order" />
        <button type="submit"><fmt:message key="finalize_order"/></button>
     </form>
    </c:if>
    <c:if test="${sessionScope.chosen == null}">
        <h3><fmt:message key="empty_cart_label"/></h3>
    </c:if>
</body>
</html>