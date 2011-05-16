<%@page contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>丽珍(傻猪相片集)</title>
        <style>
            .content{color:#777;font:12px/1.4 "helvetica neue",arial,sans-serif;width:620px;margin:20px auto;}
            h1{font-size:12px;font-weight:normal;color:#ddd;margin:0;}
            p{margin:0 0 20px}
            a {color:#22BCB9;text-decoration:none;}
            .cred{margin-top:20px;font-size:11px;}
            
            /* This rule is read by Galleria to define the gallery height: */
            #galleria{height:320px;}
            
        </style>
        
        <!-- load jQuery -->
        <script src="js/jquery-1.4.2.min.js"></script>
        
        <!-- load Galleria -->
        <script src="js/galleria/galleria-1.2.2.min.js"></script>

    </head>
<body>
    <div class="content">
        <h1>可愛的傻豬：</h1>
        <p>我愿化身石桥，受五百年风吹 五百年日晒 五百年雨打，只求能夠化解對你心中的傷害！</p>
        
        <div id="galleria">
        	<c:forEach items="${imagePath}" var="image">
        		<c:out value="${image}"></c:out>
        		<a href="<c:out value="${image}"></c:out>">
        			<img src="<c:out value="${image}"></c:out>" />
        		</a>
        	</c:forEach>
        </div>
        
        <p class="cred">Made by 猪.</p>
    </div>
    <script>

    // Load the classic theme
    Galleria.loadTheme('js/galleria/themes/classic/galleria.classic.min.js');
    
    // Initialize Galleria
    $('#galleria').galleria();

    </script>
    </body>
</html>