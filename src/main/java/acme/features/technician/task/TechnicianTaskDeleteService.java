
package acme.features.technician.task;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.mappings.InvolvedIn;
import acme.entities.tasks.Task;
import acme.entities.tasks.TaskType;
import acme.realms.technicians.Technician;

@GuiService
public class TechnicianTaskDeleteService extends AbstractGuiService<Technician, Task> {

	// Internal state ------------------------------------------------------------

	@Autowired
	private TechnicianTaskRepository repository;

	// AbstractGuiService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Task task;
		Technician technician;

		masterId = super.getRequest().getData("id", int.class);
		task = this.repository.findTaskById(masterId);
		technician = task == null ? null : task.getTechnician();
		status = task != null && task.getDraftMode() && super.getRequest().getPrincipal().hasRealm(technician);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Task task;
		int id;

		id = super.getRequest().getData("id", int.class);
		task = this.repository.findTaskById(id);

		super.getBuffer().addData(task);
	}

	@Override
	public void bind(final Task task) {
		super.bindObject(task, "type", "description", "priority", "estimatedDuration");
	}

	@Override
	public void validate(final Task task) {
		;
	}

	@Override
	public void perform(final Task task) {
		Collection<InvolvedIn> involvedIns;

		involvedIns = this.repository.findInvolvedInsFromTaskId(task.getId());
		this.repository.deleteAll(involvedIns);

		this.repository.delete(task);
	}

	@Override
	public void unbind(final Task task) {
		SelectChoices types;
		Dataset dataset;

		types = SelectChoices.from(TaskType.class, task.getType());

		dataset = super.unbindObject(task, "technician.identity.name", "type", "description", "priority", "estimatedDuration", "draftMode");
		dataset.put("types", types);

		super.getResponse().addData(dataset);
	}
}
