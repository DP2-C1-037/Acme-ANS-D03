
package acme.constraints;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.entities.airline.AirlineManager;

public class IdentifierNumberValidator extends AbstractValidator<ValidIdentifierNumber, AirlineManager> {

	@Override
	public boolean isValid(final AirlineManager airlineManager, final ConstraintValidatorContext context) {
		String initials = IdentifierNumberValidator.getInitials(airlineManager.getUserAccount().getIdentity().getFullName());
		String identifierNumberToValidate = airlineManager.getIdentifierNumber().substring(0, initials.length());
		boolean result = initials.equals(identifierNumberToValidate);
		if (!result)
			super.state(context, false, "identifierNumber", "acme.validation.flight.identifierNumber.message");
		return result;
	}

	private static String getInitials(final String fullName) {
		return Arrays.stream(fullName.split(" ")).map(p -> String.valueOf(p.charAt(0))).collect(Collectors.joining());
	}
}
