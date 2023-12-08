<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<meta charset="UTF-8">
<!DOCTYPE html>
<html>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/movieEdit.css"/>
<head>
    <title>Изменение курса</title>
</head>
<body>
    <c:if test="${sessionScope.role == null || !sessionScope.role eq 'admin'}" >
        <c:redirect url="/pages/error403.jsp"/>
    </c:if>
    <h1>Изменение курса</h1>
    <form method="post" action="/main" accept-charset="UTF-8">
        <input type="hidden" name="command" value="movie_edit">
        <label for="courseName">Название курса:</label>
        <input type="text" id="movieName" name="m_name" value="${movie.name}"><br><br>

        <label for="moviePrice">Стоимость просмотра:</label>
        <input type="text" id="moviePrice" name="m_price" value="${movie.price}"><br><br>

        <label for="movieDirector">Режиссер фильма:</label>
        <input type="text" id="movieDirector" name="m_director" value="${movie.author}"><br><br>

        <label for="movieDescription">Описание фильма:</label>
        <textarea id="movieDescription" name="m_description">${movie.description}</textarea><br><br>

        <label for="movieGenre">Жанр фильма:</label>
        <input type="text" id="movieGenre" name="m_genre" value="${movie.genre}"><br><br>
        <button type="submit">Сохранить</button><br><br>

    </form>
    <form action="/main" method="post">
        <input type="hidden" name="command" value="redirect"/>
        <input type="hidden" name="redirect" value="true"/>
        <input type="hidden" name="page" value="/pages/main.jsp"/>
        <button type="submit">Назад</button><br><br>
    </form>
    <c:if test="${sessionScope.input_error != null}">
        <label class="error">Введенные данные для обновления некорректны!</label>
    </c:if>
</body>
</html>