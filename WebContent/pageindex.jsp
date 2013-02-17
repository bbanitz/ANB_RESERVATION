<?xml version="1.0" encoding="utf-8" ?>
<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:ice="http://www.icesoft.com/icefaces/component">
	<jsp:directive.page contentType="text/html;charset=utf-8" />
	<f:view>
		<ice:outputDeclaration doctypeRoot="HTML"
			doctypePublic="-//W3C//DTD HTML 4.01 Transitional//EN"
			doctypeSystem="http://www.w3.org/TR/html4/loose.dtd" />
		<html>
		<head>
		<title><ice:outputText value="ICEfaces, Ajax for Java EE" /></title>
		<ice:outputStyle href="./xmlhttp/css/rime/rime.css" />
		</head>
		<body>
		<ice:form>
			<ice:outputText value="Thank you for using ICEfaces." />
			<ice:dataTable border="1">
				<ice:column id="column1">
					<f:facet name="header">
						<ice:outputText value="column1"></ice:outputText>
					</f:facet>
				</ice:column>
				<ice:column id="column2">
					<f:facet name="header">
						<ice:outputText value="column2"></ice:outputText>
					</f:facet>
				</ice:column>
			</ice:dataTable>

			<ice:messages />

		</ice:form>

		</body>
		</html>
	</f:view>
</jsp:root>