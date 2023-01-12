<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="formData" class="com.ilbolan.pitoswebproject.forms.ResetEmailForm" scope="request" />

<!DOCTYPE html>
<html>

<%@ include file="/WEB-INF/segments/head.jspf"%>

<body>
<div class="container">
  <%@ include file="/WEB-INF/segments/header-pages.jspf"%>

  <main>
    <h1>Επαναφορά Κωδικού</h1>

    <article>
      <c:choose>
        <c:when test="${requestScope['success']}">
          <div class="success-message">
            Στάλθηκε μήνυμα για τη συνέχεια της διαδικασίας reset του κωδικού στο e-mail σας.
          </div>
        </c:when>
        <c:otherwise>

          <c:if test="${requestScope['errors']!=null}">
            <div class="error-message">${requestScope['errors']} </div>
          </c:if>

          <p>Εισάγετε το e-mail με το οποίο κάνατε την εγγραφή σας:</p>
          <form action="password-reset" method="POST" class="form contact">
            <label for="txtEmail">Email: </label>
            <input type="email" id="txtEmail" name="email" size="40" value="${formData.email}" required>

            <input type="submit" value="Αποστολή">
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
