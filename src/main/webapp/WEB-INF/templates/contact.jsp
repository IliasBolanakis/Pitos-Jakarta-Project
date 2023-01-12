<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="formData" class="com.ilbolan.pitoswebproject.forms.ContactForm" scope="request" />

<!DOCTYPE html>
<html>

<%@ include file="/WEB-INF/segments/head.jspf"%>

<body>
<div class="container">
  <%@ include file="/WEB-INF/segments/header-pages.jspf"%>
  <main>
    <h1>Επικοινωνία</h1>
    <article>
      <c:choose>
        <c:when test="${requestScope['success']}">
          <div class="success-message">
            Παραλάβαμε το μήνυμα σας, θα επικοινωνήσουμε μαζί σας σύντομα!
          </div>
        </c:when>
        <c:otherwise>

          <c:if test="${requestScope['errors']!=null}">
            <div class="error-message">${requestScope['errors']} </div>
          </c:if>

          <form action="contact" method="POST" class="form contact">
            <label for="txtName">Ονοματεπώνυμο(*):</label>
            <input type="text" id="txtName" name="fullName" minlength="3" maxlength="40" size="40" value="${formData.fullName}" required>

            <label for="txtEmail">E-mail(*): </label>
            <input type="email" id="txtEmail" name="email" size="40" value="${formData.email}" required>

            <label for="txtTel">Τηλέφωνο: </label>
            <input type="tel" id="txtTel" name="tel" size="10" value="${formData.tel}">

            <label for="txtMessage">Μήνυμα(*): </label>
            <textarea id="txtMessage" name="message" rows="6" cols="40" required>${formData.message}</textarea>

            <span>(*) Τα πεδία είναι υποχρεωτικά</span>
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
