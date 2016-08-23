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
    <h3>${filePath}</h3>
    <c:set var="test" value='${filePath}'/>
    <c:set var="test2" value="test1"/>
    <%
    	String file="";
    	file+=(String)pageContext.getAttribute("test");
    	System.out.println(file);
    	//BufferedImage bimg=ImageIO.read(new File(file));
    	//int width = bimg.getWidth();
    	//int height = bimg.getHeight();
    	//System.out.println(width+"   "+height);
    %>
    <img src="/home/michalr/Obrazy/landscape-mountains-nature-lake.jpg" alt="asd" height="4288" width="2848">
    <a href="http://localhost:8080/Watermarks/index.html">Upload another file!</a>
</body>
</html>