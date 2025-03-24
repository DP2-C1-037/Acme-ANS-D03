
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.realms.Customer;
import acme.realms.CustomerRepository;

public class CustomerIdentifierValidator extends AbstractValidator<ValidCustomerIdentifier, Customer> {

	@Autowired
	private CustomerRepository repository;


	@Override
	public boolean isValid(final Customer customer, final ConstraintValidatorContext context) {

		assert context != null;

		boolean result;

		if (customer == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");
		else {
			{
				boolean uniqueCustomerIdentifier;
				Customer existingCustomer;

				existingCustomer = this.repository.findCustomerByIdentifier(customer.getIdentifier());
				uniqueCustomerIdentifier = existingCustomer == null || existingCustomer.equals(customer);

				super.state(context, uniqueCustomerIdentifier, "customers", "acme.validation.customer.identifier.unique.message");
			}
			{
				boolean identifierCorrectSyntax;
				String name = customer.getIdentity().getName().trim();
				String surname = customer.getIdentity().getSurname().trim();
				String initials = "" + name.charAt(0) + surname.charAt(0);

				String identifier = customer.getIdentifier().trim();
				String identifierPrefix = identifier.substring(0, initials.length());

				identifierCorrectSyntax = initials.equals(identifierPrefix);

				super.state(context, identifierCorrectSyntax, "customers", "acme.validation.customer.identifier.syntax.message");
			}
		}

		result = !super.hasErrors(context);

		return result;
	}

}
