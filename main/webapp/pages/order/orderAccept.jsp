<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/orderAccept.css" />
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="accept_orders"/></title>
</head>
<body>
    <c:if test="${sessionScope.role == null || !sessionScope.role eq 'admin'}" >
        <c:redirect url="/pages/error403.jsp"/>
    </c:if>
    <c:if test="${applicationScope.orders != null && !applicationScope.orders.isEmpty()}">
        <c:forEach var="order" items="${applicationScope.orders}">
            <h3><fmt:message key="order_number"/>${order.id}</h3>
            <h4><fmt:message key="creation_date"/> ${order.creationDate}</h4>
            <h4><fmt:message key="summary_price"/> ${order.summaryPrice}</h4>
            <form action="/main" method="post">
            <input type="hidden" name="command" value="change_order_status">
            <c:if test="${sessionScope.changed_orders == null}">
            <c:if test="${order.isAccepted()}">
                <button type="submit" name="orderId" value="${orders.indexOf(order)}"><fmt:message key="undo_accept"/></button>
            </c:if>
            <c:if test="${!order.isAccepted()}">
                <button type="submit" name="orderId" value="${orders.indexOf(order)}" ><fmt:message key="accept_order"/></button>
            </c:if>
            </c:if>
             <c:if test="${sessionScope.changed_orders != null}">
                <c:if test="${order.isAccepted() && sessionScope.changed_orders.contains(order)}">
                     <button type="submit" name="orderId" value="${orders.indexOf(order)}"><fmt:message key="accept_order"/></button>
                </c:if>
                <c:if test="${order.isAccepted() && !sessionScope.changed_orders.contains(order)}">
                     <button type="submit" name="orderId" value="${orders.indexOf(order)}"><fmt:message key="undo_accept"/></button>
                </c:if>
                <c:if test="${!order.isAccepted() && sessionScope.changed_orders.contains(order)}">
                     <button type="submit" name="orderId" value="${orders.indexOf(order)}" ><fmt:message key="undo_accept"/></button>
                </c:if>
                <c:if test="${!order.isAccepted() && !sessionScope.changed_orders.contains(order)}">
                     <button type="submit" name="orderId" value="${orders.indexOf(order)}" ><fmt:message key="accept_order"/></button>
                </c:if>
            </c:if>
            </form>
            <form action="/main" method="post">
                <input type="hidden" name="command" value="order_delete">
                <button type="submit" name="orderId" value="${orders.indexOf(order)}"><fmt:message key="delete_order"/></button>
            </form>
            <br><br>
            <c:if test="${sessionScope.changed_orders.contains(order)}">
               <h4><fmt:message key="unsaved_changes_label"/></h4>
               <br><br>
            </c:if>

        </c:forEach>
    </c:if>
    <form action="/main" method="get">
           <input type="hidden" name="command" value="orders_accept" />
           <button type="submit"><fmt:message key="save_changes"/></button>
    </form>
    <form action="/main" method="get">
        <input type="hidden" name="command" value="redirect" />
        <input type="hidden" name="redirect" value="true" />
        <input type="hidden" name="page" value="/pages/main.jsp" />
        <button type="submit"><fmt:message key="back_button"/></button>
    </form><br><br>
    <form action="/main" method="get">
       <input type="hidden" name="command" value="prev_orders_page" />
       <c:choose>
           <c:when test="${applicationScope.orders_offset > 0}">
               <button type="submit"><fmt:message key="prev_page"/></button>
           </c:when>
           <c:otherwise>
               <button type="submit" disabled><fmt:message key="prev_page"/></button>
           </c:otherwise>
       </c:choose>
    </form>
    <form action="/main" method="get">
        <input type="hidden" name="command" value="next_orders_page" />
        <c:choose>
            <c:when test="${applicationScope.orders_offset + 10 > applicationScope.orders_count}">
                <button type="submit" disabled><fmt:message key="next_page"/></button>
            </c:when>
            <c:otherwise>
                <button type="submit"><fmt:message key="next_page"/></button>
            </c:otherwise>
        </c:choose>
    </form>
</body>
</html>