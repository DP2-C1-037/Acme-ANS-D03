
package acme.features.assistanceAgent.claim;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.airline.Leg;
import acme.entities.claim.Claim;
import acme.entities.trackingLog.TrackingLog;
import acme.realms.assistanceAgent.AssistanceAgent;

@Repository
public interface AssistanceAgentClaimRepository extends AbstractRepository {

	@Query("select c from Claim c where (c.status=acme.entities.claim.ClaimStatus.ACCEPTED or c.status = acme.entities.claim.ClaimStatus.DENIED) and c.assistanceAgent.id = :assistanceAgentId")
	Collection<Claim> findCompletedClaimsByAssistanceAgentId(int assistanceAgentId);

	@Query("select c from Claim c where c.status = acme.entities.claim.ClaimStatus.PENDING and c.assistanceAgent.id = :assistanceAgentId")
	Collection<Claim> findUndergoingClaimsByAssistanceAgentId(int assistanceAgentId);

	@Query("select c from Claim c where c.id = :id")
	Claim findClaimById(int id);

	@Query("select c from Claim c where c.assistanceAgent.id = :assistanceAgentId")
	Collection<Claim> findAllClaimsByAssistanceAgentId(int assistanceAgentId);

	@Query("select a from AssistanceAgent a where a.id = :assistanceAgentId")
	AssistanceAgent findAssistanceAgentById(int assistanceAgentId);

	@Query("select l from Leg l where l.status <> acme.datatypes.LegStatus.CANCELLED")
	Collection<Leg> findOccuredLegs();

	@Query("select l from Leg l where l.id = :legId")
	Leg findLegById(int legId);

	@Query("select t from TrackingLog t where t.claim.id = :claimId")
	Collection<TrackingLog> findAllTrackingLogsByClaimId(int claimId);
}
