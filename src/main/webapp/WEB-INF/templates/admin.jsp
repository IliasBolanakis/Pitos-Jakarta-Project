<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<%@ include file="/WEB-INF/segments/head.jspf"%>


<body>
<div class="container">
    <%@ include file="/WEB-INF/segments/header-pages.jspf"%>

    <main>
        <h1>Ενέργειες Διαχειριστή:</h1>
        <article>
            <ul>
                <li>
                    Ενέργειες Διαχειριστή:
                    <ul>
                        <li><a href="admin?action=1">Διαγραφή χρηστών που δεν έχουν επαληθεύσει την εγγραφή τους (${requestScope['adminStats'][0]} εγγραφές)</a></li>
                        <li><a href="admin?action=2">Όλοι οι κωδικοί ενεργοποίησης των επαληθευμένων εγγραφών να τεθούν NULL(${requestScope['adminStats'][1]} εγγραφές)</a></li>
                    </ul>
                </li>
            </ul>
        </article>

        <%@ include file="/WEB-INF/segments/aside.jspf"%>
    </main>

    <%@ include file="/WEB-INF/segments/footer.jspf"%>
</div>
</body>


</html>