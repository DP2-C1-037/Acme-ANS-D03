
package acme.features.customer.passenger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.controllers.AbstractGuiController;
import acme.client.controllers.GuiController;
import acme.entities.passenger.Passenger;
import acme.realms.Customer;

@GuiController
public class PassengerController extends AbstractGuiController<Customer, Passenger> {

	// Internal state ---------------------------------------------------------

	@Autowired
	PassengerListService	listService;

	@Autowired
	PassengerShowService	passengerService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.passengerService);

	}

}
