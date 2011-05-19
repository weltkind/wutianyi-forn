<%@page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<html>
	<head>
		<title>struts2 示例</title>
	</head>
	
	<body>
		<h2>
			<s:property value="message"/>
		</h2>
		${message }
	</body>
</html>