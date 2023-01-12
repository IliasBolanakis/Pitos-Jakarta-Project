<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="formOrder" class="com.ilbolan.pitoswebproject.forms.OrderForm" scope="request" />
<jsp:useBean id="user" class="com.ilbolan.pitoswebproject.models.beans.User" scope="session" />

<!DOCTYPE html>
<html>

<%@ include file="/WEB-INF/segments/head.jspf"%>

<body>
<div class="container">
  <%@ include file="/WEB-INF/segments/header-pages.jspf"%>

  <main>
    <h1>Οι πίτες μας στο χώρο σας! </h1>
    <article>
      <p>
        Ωράριο λήψης παραγγελιών (12:00-22:00)
      </p>
      <c:choose>
        <c:when test="${requestScope['success']}">
          <div class="success-message">
            Παραλάβαμε την παραγγελία σας και οι πίτες μας θα είναι σύντομα κοντά σας!
          </div>
        </c:when>
        <c:otherwise>

          <c:choose>
            <c:when test="${user.id==0}">
              <p>
                <a href="login?previouspage=buy">Συνδεθείτε</a> για να δείτε το ιστορικό των παραγγελιών σας (ή να επαναλάβετε κάποια παλιά παραγγελία σας)
              </p>
            </c:when>
            <c:otherwise>
              <p>
                Καλώς ήλθατε ${user.fullName}! Μπορείτε να επαναλάβετε κάποια από τις προηγούμενες παραγγελίες σας:
              </p>
              <ul>
                <c:set var="count" value="0" scope="request"/>
                <c:forEach var="order" items="${requestScope['previousOrders']}">
                  <li>
                    <a href="buy?previousorder=${count}">${order.stamp}: ${order.orderItems}
                    </a>
                    <c:set var="count" value="${count + 1}" scope="request"/>
                  </li>
                </c:forEach>
                <c:if test="${count == 0}">
                  <li>Καμία Παραγγελία</li>
                </c:if>
              </ul>
            </c:otherwise>
          </c:choose>

          <c:if test="${requestScope['errors']!=null}">
            <div class="error-message">${requestScope['errors']} </div>
          </c:if>
          <form action="buy" method="POST" class="form buy">
            <section class="customer-info">
              <h3>
                Πληροφορίες Παράδοσης:
              </h3>
              <label for="txtName">Ονοματεπώνυμο(*):</label>
              <input type="text" id="txtName" name="name" minlength="3" maxlength="40" size="40" required
                     value="${empty formOrder.fullName ? user.fullName : formOrder.fullName}">
              <label for="txtAddress">Διεύθυνση(Οδός - Αριθμός)(*):</label>
              <input type="text" id="txtAddress" name="address" minlength="5" maxlength="40" size="40" required
                     value="${empty formOrder.address ? user.address : formOrder.address}">
              <label for="slArea"> Περιοχή(*):</label>
              <select id="slArea" name="area" required>
                <c:forEach var="item" items="${requestScope['areas']}">
                  <option value="${item.id}" ${formOrder.areaId == item.id ? "selected" : ""}>${item.description}</option>
                </c:forEach>
              </select>
              <label for="txtEmail">E-mail(*): </label>
              <input type="email" id="txtEmail" name="email" size="40" required
                     value="${empty formOrder.email ? user.email : formOrder.email}">
              <label for="txtTel">Τηλέφωνο(*): </label>
              <input type="tel" id="txtTel" name="tel" size="15" required
                     value="${empty formOrder.tel ? user.tel : formOrder.tel}">
              <label for="txtMessage">Ειδικές Πληροφορίες για την παράδοση: </label>
              <textarea id="txtMessage" name="message" rows="6" cols="40">${formOrder.comments}</textarea>
              <span>(*) Τα πεδία είναι υποχρεωτικά</span>
            </section>

            <section class="order">
              <h3>
                Παραγγελία:
              </h3>

              <label for="txtSpanakopita">Σπανακόπιτες: </label>
              <input type="number" id="txtSpanakopita" name="OrderSpanakopites" min="0" max="100"
                     value="${empty formOrder.orderItems?
                                   (empty requestScope['spanakopites'] ? 0 : requestScope['spanakopites']):
                                   formOrder.orderItems[0].quantity}">

              <label for="txtManitaropita">Μανιταρόπιτες: </label>
              <input type="number" id="txtManitaropita" name="OrderManitaropites" min="0" max="100"
                     value="${empty formOrder.orderItems?
                                   (empty requestScope['manitaropites'] ? 0 : requestScope['manitaropites']):
                                   formOrder.orderItems[1].quantity}">

              <label for="txtPrasopita">Πρασόπιτες: </label>
              <input type="number" id="txtPrasopita" name="OrderPrasopites" min="0" max="100"
                     value="${empty formOrder.orderItems?
                                   (empty requestScope['prasopites'] ? 0 : requestScope['prasopites']):
                                   formOrder.orderItems[2].quantity}">

              <label for="txtBoureki">Μπουρέκια: </label>
              <input type="number" id="txtBoureki" name="OrderBourekia" min="0" max="100"
                     value="${empty formOrder.orderItems?
                                   (empty requestScope['bourekia'] ? 0 : requestScope['bourekia']):
                                   formOrder.orderItems[3].quantity}">

              <input type="checkbox" id="chkΟffer1" name="offer1" value="offer1-on" ${formOrder.offer==true?"checked":""}>
              <label for="chkΟffer1">Ενσωμάτωση προσφοράς στις 10 η 1 πίτα δώρο</label>
            </section>

            <section class="payment">
              <h3>
                Τρόπος Πληρωμής:
              </h3>
              <div class="radio-buttons">
                <input type="radio" id="rdVISA" name="payment" value="visa" ${formOrder.payment=='visa' ? "checked":""}>
                <label for="rdVISA">VISA</label>
                <input type="radio" id="rdCash" name="payment" value="cash" ${formOrder.payment=='cash' ? "checked":""}>
                <label for="rdCash">Μετρητά</label>
                <input type="radio" id="rdSpecial" name="payment" value="special" ${formOrder.payment=='special' ? "checked":""}>
                <label for="rdSpecial">Είδος</label>
              </div>
            </section>

            <div class="buttons">
              <input type="submit" value="Αποστολή">
              <input type="reset" value="Καθαρισμός">
            </div>
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
