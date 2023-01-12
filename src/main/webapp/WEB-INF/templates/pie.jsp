<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="bean" scope="request" class="com.ilbolan.pitoswebproject.models.beans.Pie" />
<!DOCTYPE html>
<html>

<%@ include file="/WEB-INF/segments/head.jspf"%>

<body>
  <div class="container">
    <%@ include file="/WEB-INF/segments/header-pages.jspf"%>

    <main>
      <h1>${bean.name}</h1>

      <div class="pie">
        <img src="${pageContext.request.contextPath}${bean.fileName}" alt="Εικόνα - ${bean.name}" />
        <p>Υλικά: ${bean.ingredients}</p>
        <p>Τιμή: ${bean.price}€</p>
      </div>

      <%@ include file="/WEB-INF/segments/aside.jspf"%>
    </main>

    <%@ include file="/WEB-INF/segments/footer.jspf"%>

  </div>
</body>


</html>
