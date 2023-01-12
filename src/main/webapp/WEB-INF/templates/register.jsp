<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="formData" class="com.ilbolan.pitoswebproject.forms.RegisterForm" scope="request" />

<!DOCTYPE html>
<html>

<%@ include file="/WEB-INF/segments/head.jspf"%>

<body>
<div class="container">
    <%@ include file="/WEB-INF/segments/header-pages.jspf"%>

    <main>
        <h1>Εγγραφή στη Σελίδα</h1>
        <article>
            <c:choose>
                <c:when test="${requestScope['success']}">
                    <div class="success-message">
                        Επιτυχής Εγγραφή! Συνδεθείτε στο email σας, για την απαραίτητη επαλήθευση!
                    </div>
                </c:when>
                <c:when test="${requestScope['registerComplete']==true}">
                    <div class="success-message">
                        Ο λογαριασμός σας επαληθεύτηκε! Προχωρήστε σε <a href="login">login</a>
                    </div>
                </c:when>
                <c:when test="${requestScope['registerComplete']==false}">
                    <div class="error-message">
                        Αποτυχία στην επαλήθευση της εγγραφής, παρακαλώ προχωρήστε ξανά σε <a href="register">εγγραφή</a>
                    </div>
                </c:when>
                <c:otherwise>

                    <c:if test="${requestScope['errors']!=null}">
                        <div class="error-message">${requestScope['errors']} </div>
                    </c:if>

                    <form action="register" method="POST" class="form contact">
                        <label for="txtName">Ονοματεπώνυμο(*):</label>
                        <input type="text" id="txtName" name="fullName" minlength="3" maxlength="40" size="40" value="${formData.fullName}" required>

                        <label for="txtEmail">E-mail(*): </label>
                        <input type="email" id="txtEmail" name="email" size="40" value="${formData.email}" required>

                        <label for="txtEmail">Διεύθυνση(*): </label>
                        <input type="text" id="txtAddress" name="address" size="40" value="${formData.address}" required>

                        <label for="txtTel">Τηλέφωνο: </label>
                        <input type="tel" id="txtTel" name="tel" size="10" value="${formData.tel}">

                        <label for="txtUsername">Username (*):</label>
                        <input type="text" id="txtUsername" name="username" maxlength="30" value="${formData.username}" required>

                        <label for="txtPassword">Password (*): </label>
                        <input type="password" id="txtPassword" name="password" maxlength="30" required>

                        <label for="txtPassword2">Password(ξανά)(*): </label>
                        <input type="password" id="txtPassword2" name="passwordConfirm" maxlength="30" required>

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
