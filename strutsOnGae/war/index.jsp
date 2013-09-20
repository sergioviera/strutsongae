<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
	<title>Angry Indec</title>
	<link href="/favicon.ico" rel="favicon" />
	<link type="text/css" rel="stylesheet" href="/css/styles.css" />
</head>
<body>
<div id="body">
	<div id="menu">
		<ul>
		<s:if test="#session.authProvider==null">
			<script>var isLoggedOn = false;</script>
			<li><a href="login?id=facebook"><img src="img/btn/facebook.png" alt="Logearse con Facebook" title="Logearse con Facebook"/></a></li>
			<li><a href="login?id=twitter"><img src="img/btn/twitter.png" alt="Logearse con Twitter" title="Logearse con Twitter"/></a></li>
		</s:if>
		<s:else>
			<script>var isLoggedOn = true;</script>
			<li><a href="logout"><img src="<s:property value="#session.authProvider.userProfile.profileImageURL" />" alt="Click para salir" title="Click para salir"/></a></li>
		</s:else>
			<li id="us"><img src="img/btn/us.png" alt="Nosotros" title="Nosotros" /></li>
			<li><a href='mailto:sergioviera@gmail.com' target="_blank"><img src="img/btn/mail.png" alt="Enviar e-mail" title="Enviar e-mail" /></a></li>
		</ul>
	</div>
</div>
</body>
</html>