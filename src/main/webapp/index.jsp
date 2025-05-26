<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Simple To-Do List with Edit/Delete</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
</head>
<body>
    <h1>My To-Do List</h1>

    <!-- Add new task -->
    <form action="${pageContext.request.contextPath}/todo" method="post">
        <input type="hidden" name="action" value="add" />
        <input type="text" name="item" placeholder="Add new task" required />
        <button type="submit">Add</button>
    </form>

    <hr />

    <ul>
        <c:choose>
            <c:when test="${not empty sessionScope.todoList}">
                <c:forEach var="task" items="${sessionScope.todoList}" varStatus="status">
                    <li>
                        <c:choose>
                            <c:when test="${param.editIndex == status.index}">
                                <form action="${pageContext.request.contextPath}/todo" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="edit" />
                                    <input type="hidden" name="index" value="${status.index}" />
                                    <input type="text" name="item" value="${task}" required />
                                    <button type="submit">Save</button>
                                    <a href="${pageContext.request.contextPath}/todo">Cancel</a>
                                </form>
                            </c:when>
                            <c:otherwise>
                                ${task}
                                <form action="${pageContext.request.contextPath}/todo" method="get" style="display:inline;">
                                    <input type="hidden" name="editIndex" value="${status.index}" />
                                    <button type="submit" class="edit-btn">Edit</button>
                                </form>
                                 <form action="${pageContext.request.contextPath}/todo" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="delete" />
                                    <input type="hidden" name="index" value="${status.index}" />
                                    <button type="submit" onclick="return confirm('Delete this task?');">Delete</button>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <li>No tasks yet</li>
            </c:otherwise>
        </c:choose>
    </ul>
    