
package acme.features.administrator.aircraft;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Administrator;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.datatypes.AircraftStatus;
import acme.entities.aircraft.Aircraft;
import acme.entities.airline.Airline;
import acme.entities.airline.AirlineRepository;

@GuiService
public class AdministratorAircraftCreateService extends AbstractGuiService<Administrator, Aircraft> {

	@Autowired
	private AdministratorAircraftRepository	repository;

	@Autowired
	private AirlineRepository				repositoryAirline;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Aircraft aircraft;

		aircraft = new Aircraft();

		super.getBuffer().addData(aircraft);
	}

	@Override
	public void bind(final Aircraft aircraft) {
		super.bindObject(aircraft, "model", "registrationNumber", "capacity", "cargoWeight", "status", "optionalDetails");
	}

	@Override
	public void validate(final Aircraft aircraft) {
		boolean confirmation;

		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "acme.validation.confirmation.message");
	}

	@Override
	public void perform(final Aircraft aircraft) {
		this.repository.save(aircraft);
	}

	@Override
	public void unbind(final Aircraft aircraft) {
		SelectChoices statuses;
		SelectChoices airlines;
		Collection<Airline> airlinesCollection;
		Dataset dataset;

		statuses = SelectChoices.from(AircraftStatus.class, aircraft.getStatus());
		// airlinesCollection = this.repositoryAirline.findAllAirlines();
		// airlines = SelectChoices.from(airlinesCollection, "name", aircraft.getAirline());

		dataset = super.unbindObject(aircraft, "model", "registrationNumber", "capacity", "cargoWeight", "status", "optionalDetails");
		dataset.put("confirmation", false);
		dataset.put("readonly", false);
		dataset.put("statuses", statuses);
		// dataset.put("airlines", airlines);

		super.getResponse().addData(dataset);
	}

}
