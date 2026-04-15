package uk.gov.hmcts.reform.dev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gov.hmcts.reform.dev.models.ExampleCase;

import java.util.Optional;

public interface ExampleCaseRepository extends JpaRepository<ExampleCase, Integer> {

    Optional<ExampleCase> findFirstByOrderByIdAsc();
}
