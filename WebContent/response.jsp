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
<link rel="stylesheet" href="${pageContext.request.contextPath}/Resources/style.css" />
</head>
<body>
	<%-- Using JSP EL to get message attribute value from request scope --%>
    <h2>${requestScope.message}</h2>
    <br>
    <div class="crop">
    	<img src="${filePath3}" alt="asddd" height="${height}" width="${width}">
    </div>
    <br>
    <br>
    <br>
    <br>
    <p><a href="http://localhost:8080/Watermarks/index.html">Upload another file!</a></p>
</body>
</html>