
package acme.features.aircraft;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.aircraft.Aircraft;

@Repository
public interface AircraftRepository extends AbstractRepository {

	@Query("select a from Aircraft a where a.registrationNumber = :registrationNumber")
	Aircraft findAircraftByRegistrationNumber(String registrationNumber);

}
