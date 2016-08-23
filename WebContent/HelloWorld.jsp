<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<center><font color="gray" size="7">Hello World!</font></center>
	
	<%
		//To jest skryplet. Zauwaz, że zmienna
		//"date" zadeklarowana w pierwszym wbudowanym
		//wyrażeniu jest dostępna równiez w tym późniejszym.
		System.out.println("Test helloworld");
		java.util.Date date = new java.util.Date();
	%>
	
	Obecnie mamy <%= date %>
</body>
</html>