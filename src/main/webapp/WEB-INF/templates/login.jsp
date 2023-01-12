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
    <h1>Σύνδεση στη Σελίδα</h1>
    <article>
      <c:choose>
        <c:when test="${requestScope['success']}">
          <div class="success-message">
            Επιτυχής Σύνδεση! Συνεχίστε την πλοήγηση σας στη σελίδα!
          </div>
        </c:when>
        <c:otherwise>

          <c:if test="${requestScope['errors']!=null}">
            <div class="error-message">${requestScope['errors']} </div>
          </c:if>

          <form action="login" method="POST" class="form contact">
            <label for="txtUsername">Username:</label>
            <input type="text" id="txtUsername" name="username" maxlength="30" value="${formData.username}" required>

            <label for="txtPassword">Password: </label>
            <input type="password" id="txtPassword" name="password" maxlength="30" required>

            <a href="password-reset">Ξεχάσατε τον κωδικό σας;</a>

            <input type="submit" value="Αποστολή">

            <!-- Set the CSRF token as hidden part of the form -->
            <input type="hidden" name="csrfToken" value=${sessionScope.csrfToken}>
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
