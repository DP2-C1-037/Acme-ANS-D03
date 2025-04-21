
package acme.features.flightCrewMember.flightAssignment;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.activityLog.ActivityLog;
import acme.entities.airline.Leg;
import acme.entities.flightAssignment.FlightAssignment;
import acme.realms.flightCrewMember.FlightCrewMember;

@Repository
public interface FlightCrewMemberFlightAssignmentRepository extends AbstractRepository {

	@Query("select fa from FlightAssignment fa")
	Collection<FlightAssignment> findAllFlightAssignments();

	@Query("select fa from FlightAssignment fa where fa.id = :id")
	FlightAssignment findFlightAssignmentById(int id);

	@Query("""
		SELECT fa
		FROM FlightAssignment fa
		WHERE
		    (fa.draftMode = false AND fa.leg.scheduledArrival < :now AND (fa.flightCrewMember IS NULL OR fa.flightCrewMember.id != :memberId))
		    OR
		    (fa.leg.scheduledArrival < :now AND fa.flightCrewMember.id = :memberId)
		""")
	Collection<FlightAssignment> findCompletedPublishedOrMemberAssignments(int memberId, Date now);

	@Query("""
		SELECT fa
		FROM FlightAssignment fa
		WHERE
		    (fa.draftMode = false AND fa.leg.scheduledDeparture > :now AND (fa.flightCrewMember IS NULL OR fa.flightCrewMember.id != :memberId))
		    OR
		    (fa.leg.scheduledDeparture > :now AND fa.flightCrewMember.id = :memberId)
		""")
	Collection<FlightAssignment> findPlannedPublishedOrMemberAssignments(int memberId, Date now);

	@Query("select l from Leg l")
	Collection<Leg> findAllLegs();

	@Query("select l from Leg l where l.id = :legId")
	Leg findLegById(int legId);

	@Query("select m from FlightCrewMember m")
	Collection<FlightCrewMember> findAllFlightCrewMembers();

	@Query("select m from FlightCrewMember m where m.id = :memberId")
	FlightCrewMember findFlightCrewMemberById(int memberId);

	@Query("select l from ActivityLog l where l.flightAssignment.id = :id")
	Collection<ActivityLog> findActivityLogsByAssignmentId(int id);

	@Query("select distinct fa.leg from FlightAssignment fa where fa.flightCrewMember.id = :memberId")
	Collection<Leg> findLegsByFlightCrewMemberId(int memberId);

	@Query("select fa from FlightAssignment fa where fa.leg.id = :legId")
	Collection<FlightAssignment> findFlightAssignmentByLegId(int legId);

	@Query("select fa from FlightAssignment fa where fa.draftMode = false and (fa.flightCrewMember is null or fa.flightCrewMember.id != :memberId)")
	Collection<FlightAssignment> findPublishedAssignmentsExcludingMember(int memberId);

}
