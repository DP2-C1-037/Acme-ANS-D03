<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="airline-manager.leg.list.label.status" path="status" width="33%"/>	
	<acme:list-column code="airline-manager.leg.list.label.scheduledDeparture" path="scheduledDeparture" width="34%"/>
	<acme:list-column code="airline-manager.leg.list.label.arrivalAirport" path="arrivalAirport" width="33%"/>
	
	<acme:list-payload path="payload"/>	
</acme:list>

<jstl:if test="${_command == 'list'}">
	<acme:button code="airline-manager.leg.list.button.create" action="/airline-manager/leg/create"/>
</jstl:if>	
