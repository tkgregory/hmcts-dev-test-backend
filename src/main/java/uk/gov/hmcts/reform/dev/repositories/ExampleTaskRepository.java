package uk.gov.hmcts.reform.dev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gov.hmcts.reform.dev.models.ExampleTask;

import java.util.Optional;

public interface ExampleTaskRepository extends JpaRepository<ExampleTask, Integer> {

    Optional<ExampleTask> findFirstByOrderByIdAsc();
}
