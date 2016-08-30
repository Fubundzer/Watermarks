<%@page import="java.io.File"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Upload File Response</title>
</head>
<body>
	<%-- Using JSP EL to get message attribute value from request scope --%>
    <h2>${requestScope.message}</h2>
    <h3>${requestScope.filePath3}</h3>
    <h4>${pageContext.request.contextPath}</h4>
    <br>
    <div class="crop">
    	<img src="${requestScope.filePath}" alt="asddd" height="${height2}" width="${width2}">
    </div>
    <p><a href="http://localhost:8080/Watermarks/index.html">Upload another file!</a></p>
</body>
</html>