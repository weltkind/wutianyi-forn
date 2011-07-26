
<%@page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<<script type="text/javascript">
<!--
function test(information) {
	alert(information);
}
//-->
</script>
<c:if test="${error != null}">
	error:
	<c:out value="${error}"></c:out>
	<br/>
</c:if>
<c:if test="${errorInformation != null}">
	errorInformation:
	<c:out value="${errorInformation}"></c:out>
</c:if>
<c:out value="${errorInformation}"></c:out>
<input type="button" onclick="test(<c:out value="${errorInformation}"></c:out>);"/>