<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<%@ include file="/WEB-INF/segments/head.jspf"%>

<body>
  <div class="container">
    <%@ include file="/WEB-INF/segments/header-pages.jspf"%>

    <main>
      <h1>Κατάστημα</h1>
      <div class="slider">
        <div class="slide slide1">
          <figure>
            <img src="${pageContext.request.contextPath}/images/store1.jpg" alt="Εξωτερική Φωτογραφία Καταστήματος">
            <figcaption>Απολαύστε την πίτα σας με θέα τα κάτω Πατήσια</figcaption>
          </figure>
        </div>
        <div class="slide slide2">
          <figure>
            <img src="${pageContext.request.contextPath}/images/store2.jpg" alt="Εξωτερική Φωτογραφία Καταστήματος">
            <figcaption>Με άπλετο χώρο για να παίξουν τα παιδιά σας</figcaption>
          </figure>
        </div>
        <div class="slide slide3">
          <figure>
            <img src="${pageContext.request.contextPath}/images/delivery.jpg" alt="Delivery">
            <figcaption>Με χαρά τις καθημερινές κάνουμε και delivery (πάνω από 20 πίτες στην παραγγελία)</figcaption>
          </figure>
        </div>
        <a class="previous" onclick="plusSlides(-1)">⬅</a>
        <a class="next" onclick="plusSlides(1)">➡</a>
      </div>

      <%@ include file="/WEB-INF/segments/aside.jspf"%>
    </main>

    <%@ include file="/WEB-INF/segments/footer.jspf"%>
  </div>

  <script src="${pageContext.request.contextPath}/js/slider.js"></script>
</body>


</html>
