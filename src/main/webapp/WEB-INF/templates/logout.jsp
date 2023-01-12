<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="formData" class="com.ilbolan.pitoswebproject.forms.LoginForm" scope="request" />

<!DOCTYPE html>
<html>

<%@ include file="/WEB-INF/segments/head.jspf"%>

<body>
<div class="container">
  <%@ include file="/WEB-INF/segments/header-pages.jspf"%>

  <main>
    <h1>Αποσύνδεση από τη Σελίδα</h1>
    <article>
      <c:choose>
        <c:when test="${requestScope['success']}">
          <div class="success-message">
            Αποσυνδεθήκατε από τη σελίδα!
          </div>
        </c:when>
        <c:otherwise>

          <c:if test="${requestScope['errors']!=null}">
            <div class="error-message">${requestScope['errors']} </div>
          </c:if>

          <form action="logout" method="POST" class="form contact">
            <input type="submit" value="Αποσύνδεση">
          </form>
        </c:otherwise>
      </c:choose>


    </article>

    <%@ include file="/WEB-INF/segments/aside.jspf"%>
  </main>

  <%@ include file="/WEB-INF/segments/footer.jspf"%>
</div>
</body>


</html>
