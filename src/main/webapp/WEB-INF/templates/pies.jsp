<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<%@ include file="/WEB-INF/segments/head.jspf"%>

<body>
  <div class="container">
    <%@ include file="/WEB-INF/segments/header-pages.jspf"%>

    <main>
      <h1>Οι Πίτες Μας</h1>
      <article>
        <table class="table-pies">
          <thead>
            <tr>
              <th></th>
              <th>Πίτα</th>
              <th>Τιμή/τμχ</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="item" items="${requestScope.pies}">
              <tr>
                <td><img src="${pageContext.request.contextPath}${item.fileName}" alt="${item.name}"><span>${item.name}</span></td>
                <td><a href="${pageContext.request.contextPath}/pie?id=${item.id}">${item.name}</a></td>
                <td>${item.price}€</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </article>

      <%@ include file="/WEB-INF/segments/aside.jspf"%>
    </main>

    <%@ include file="/WEB-INF/segments/footer.jspf"%>

  </div>
</body>


</html>
