
package acme.constraints;

import java.text.SimpleDateFormat;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.helpers.MomentHelper;
import acme.entities.service.Service;

public class PromotionCodeValidator extends AbstractValidator<ValidPromotionCode, Service> {

	@Override
	public boolean isValid(final Service service, final ConstraintValidatorContext context) {

		boolean result = true;

		if (!(service.getPromotionCode() == null || service.getPromotionCode().equals(""))) { // servicio puede ser nulo

			SimpleDateFormat sdf = new SimpleDateFormat("yy");

			String promotionCode = service.getPromotionCode();
			if (promotionCode.length() >= 2) {
				String promotionCodeLastTwoDigits = promotionCode.substring(promotionCode.length() - 2);

				String currentYearLastTwoDigits = sdf.format(MomentHelper.getBaseMoment()); // current momeny en vez de base moment

				if (!promotionCodeLastTwoDigits.equals(currentYearLastTwoDigits) || !service.getPromotionCode().matches("^[A-Z]{4}-[0-9]{2}$")) {
					super.state(context, false, "services", "acme.validation.service.promotionCode.message");
					result = false;
				}
			} else {
				super.state(context, false, "services", "acme.validation.service.promotionCode.message");
				result = false;
			}

		}

		return result;
	}

}
