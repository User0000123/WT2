<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/myOrders.css"/>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="my_orders"/></title>
</head>
<body>
    <c:if test="${sessionScope.role == null}" >
        <c:redirect url="/pages/error403.jsp"/>
    </c:if>
    <h1><fmt:message key="my_orders"/>:</h1>
        <c:if test="${sessionScope.orders != null && !sessionScope.orders.isEmpty()}">
            <c:forEach var="order" items="${sessionScope.orders}">
            <section>
                <c:forEach var="course" items="${sessionScope.courses.get(orders.indexOf(order))}">
                <div>
                   <h3><fmt:message key="name_desc"/> ${course.name}</h3>
                   <h4><fmt:message key="main_tech"/> ${course.mainTech}</h4>
                   <h4><fmt:message key="price"/> ${course.price}</h4>
                </div>
                </c:forEach>
                <h3><fmt:message key="summary_price"/> ${order.summaryPrice}</h3>
                <h3><fmt:message key="creation_date"/> ${order.creationDate}</h3>
                <c:if test="${order.isAccepted()}">
                    <h3><fmt:message key="status"/> <span class="accepted"><fmt:message key="status_accepted"/></span></h3>
                </c:if>
                <c:if test="${!order.isAccepted()}">
                    <h3><fmt:message key="status"/> <span class="processing"><fmt:message key="status_processing"/></span></h3>
                </c:if>
            </section>
            </c:forEach>
        </c:if>
        <c:if test="${sessionScope.orders == null || sessionScope.orders.isEmpty()}">
        <h3><fmt:message key="no_orders"/></h3>
        </c:if>
    <form action="/main" method="post">
        <input type="hidden" name="command" value="main" />
        <button class="button" type="submit"><fmt:message key="back_button"/></button>
    </form>
</body>
</html>