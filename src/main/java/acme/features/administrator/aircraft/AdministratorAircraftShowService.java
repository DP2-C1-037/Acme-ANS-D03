
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

@GuiService
public class AdministratorAircraftShowService extends AbstractGuiService<Administrator, Aircraft> {

	@Autowired
	private AdministratorAircraftRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Aircraft aircraft;
		int id;

		id = super.getRequest().getData("id", int.class);
		aircraft = this.repository.findAircraftById(id);

		super.getBuffer().addData(aircraft);
	}

	@Override
	public void unbind(final Aircraft aircraft) {
		Dataset dataset;
		SelectChoices statuses;
		SelectChoices selectedAirlines;
		Collection<Airline> airlines;

		statuses = SelectChoices.from(AircraftStatus.class, aircraft.getStatus());
		airlines = this.repository.findAllAirlines();
		selectedAirlines = SelectChoices.from(airlines, "name", aircraft.getAirline());

		dataset = super.unbindObject(aircraft, "model", "registrationNumber", "capacity", "cargoWeight", "status", "optionalDetails");
		dataset.put("confirmation", false);
		dataset.put("readonly", false);
		dataset.put("statuses", statuses);
		dataset.put("airlines", selectedAirlines);
		dataset.put("airline", selectedAirlines.getSelected().getKey());

		super.getResponse().addData(dataset);
	}
}
