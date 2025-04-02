<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="airline-manager.leg.form.label.flightNumber" path="flightNumber"/>	
	<acme:input-textbox code="airline-manager.leg.form.label.scheduledDeparture" path="scheduledDeparture"/>
	<acme:input-textbox code="airline-manager.leg.form.label.scheduledArrival" path="scheduledArrival"/>
	<acme:input-select code="airline-manager.leg.form.label.status" path="status" choices= "${status}"/>
	<acme:input-textbox code="airline-manager.leg.form.label.duration" path="duration" readonly="true"/>
	<acme:input-select code="airline-manager.leg.form.label.departureAirport" path="departureAirport" choices= "${airports}"/>
	<acme:input-select code="airline-manager.leg.form.label.arrivalAirport" path="arrivalAirport" choices= "${airports}"/>
	<acme:input-select code="airline-manager.leg.form.label.flight" path="flight" choices= "${flights}"/>
	<acme:input-select code="airline-manager.leg.form.label.aircraft" path="aircraft" choices= "${aircrafts}"/>
	
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="airline-manager.leg.form.button.update" action="/airline-manager/leg/update"/>
			<acme:submit code="airline-manager.leg.form.button.delete" action="/airline-manager/leg/delete"/>
			<acme:submit code="airline-manager.leg.form.button.publish" action="/airline-manager/leg/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="airline-manager.leg.form.button.create" action="/airline-manager/leg/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>
