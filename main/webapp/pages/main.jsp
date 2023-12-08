<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
<html>
<header class="header">
    <div>
        <form action="/main" method="post">
                <input type="hidden" name="command" value="redirect"/>
                <input type="hidden" name="redirect" value="true"/>
                <input type="hidden" name="page" value="/pages/main.jsp"/>
                <input type="hidden" name="locale" value="en" />
                <button type="submit">EN</button>
        </form>
        <form action="/main" method="post">
                <input type="hidden" name="command" value="redirect"/>
                <input type="hidden" name="redirect" value="true"/>
                <input type="hidden" name="page" value="/pages/main.jsp"/>
                <input type="hidden" name="locale" value="ru" />
                <button type="submit">RU</button>
        </form>
        <c:choose>
            <c:when test="${sessionScope.login != null}">
                <h3><fmt:message key="greeting"/> ${sessionScope.login}</h3>
                <form action="/main" method="post">
                    <input type="hidden" name="command" value="logout"/>
                    <button type="submit"><fmt:message key="logout"/></button>
                </form>
                <form action="/main" method="post">
                    <input type = "hidden" name="command" value="load_user_to_edit"></input>
                    <button type = "submit"><fmt:message key="edit_profile"/></button>
                </form>
                <form action="/main" method="post">
                    <input type="hidden" name="command" value="form_my_orders">
                    <button type="submit"><fmt:message key="user_orders"/></button>
                </form>
                <form action="/main" method="post">
                  <input type="hidden" name="command" value="finalize_order">
                  <button type="submit"><fmt:message key="finalize_order"/></button><br/>
                </form>
                 <c:if test="${sessionScope.role eq 'admin'}">
                     <form action="/main" method="get">
                         <input type = "hidden" name="command" value="load_orders"></input>
                         <button type = "submit"><fmt:message key="accept_orders"/></button>
                     </form>
                     <form action="/main" method="post">
                         <input type="hidden" name="command" value="redirect">
                         <input type="hidden" name="redirect" value="true">
                         <input type="hidden" name="page" value="/pages/movie/movieCreation.jsp">
                         <button type="submit"><fmt:message key="create_movie"/></button>
                     </form>
                 </c:if>
            </c:when>
            <c:otherwise>
               <form action="/main" method="post">
                    <input type="hidden" name="command" value="redirect">
                    <input type="hidden" name="redirect" value="true">
                    <input type="hidden" name="page" value="/pages/login.jsp">
                    <button type="submit"><fmt:message key="login_label"/></button>
                </form>
                <form action="/main" method="post">
                    <input type="hidden" name="command" value="redirect">
                    <input type="hidden" name="redirect" value="true">
                    <input type="hidden" name="page" value="/pages/register.jsp">
                    <button type = "submit"><fmt:message key="reg"/></button>
                </form>
            </c:otherwise>
        </c:choose>
    </div>
</header>
<c:forEach var="movie" items="${applicationScope.movies}" >
    <section>
            <h3><fmt:message key="name_desc"/> ${movie.name}</h3>
            <h4><fmt:message key="main_tech_desc"/> ${movie.mainTech}</h4>
            <h4><fmt:message key="price_desc"/> ${movie.price}</h4>
            <div>
            <c:if test="${sessionScope.role eq 'admin'}">
                <form action="/main" method="post">
                    <input type="hidden"  name="command" value="load_movie_to_edit"></input>
                    <input type="hidden"  name="movieId" value="${movies.indexOf(movie)}"/>
                    <button type="submit"><fmt:message key="edit_movie"/></button>
                </form>
            </c:if>
            <form action="/main" method="post">
                <input type="hidden"  name="movieId" value="${movies.indexOf(movie)}"/>
                <input type="hidden"  name="command" value="shopping_cart_add"></input>
                <c:if test="${sessionScope.cart.contains(movie) && sessionScope.role != null}">
                    <button type="submit" disabled><fmt:message key="already_in_cart"/></button>
                </c:if>
                <c:if test="${!(sessionScope.cart.contains(movie) || sessionScope.role == null)}">
                    <button type="submit"><fmt:message key="add_movie_to_cart"/></button>
                </c:if>
            </form>
            <form action="/main" method="post">
                <input type = "hidden" name="command" value="view_movie"></input>
                <input type="hidden"  name="movieId" value="${movies.indexOf(movie)}"/>
                <button type = "submit"><fmt:message key="show_movie"/></button>
            </form>
            <c:if test="${sessionScope.role eq 'admin'}">
                <form action="/main" method="post">
                    <input type="hidden"  name="command" value="movie_delete"></input>
                    <button type="submit"><fmt:message key="delete_movie"/></button>
                </form>
             </c:if>
             </div>
            <h4><fmt:message key="director_desc"/> ${movie.director}</h4>
    </section>
</c:forEach>
<div>
<form action="/main" method="post">
    <c:choose>
        <c:when test="${applicationScope.offset < 10}">
            <button type="submit" disabled><fmt:message key="prev_page"/></button>
        </c:when>
        <c:otherwise>
            <button type="submit"><fmt:message key="prev_page"/></button>
        </c:otherwise>
    </c:choose>
</form>

<form action="/main" method="post">
    <input type="hidden" name="command" value="next_page"></input>
    <c:choose>
    <c:when test="${applicationScope.offset + 10 >= applicationScope.movies_count}">
        <button type="submit" disabled><fmt:message key="next_page"/></button>
    </c:when>
    <c:otherwise>
        <button type="submit"><fmt:message key="next_page"/></button>
    </c:otherwise>

    </c:choose>
</form>
</div>
</html>