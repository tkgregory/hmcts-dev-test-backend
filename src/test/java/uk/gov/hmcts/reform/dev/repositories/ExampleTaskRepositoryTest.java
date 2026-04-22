package uk.gov.hmcts.reform.dev.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uk.gov.hmcts.reform.dev.models.ExampleTask;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ExampleTaskRepositoryTest {

    @Autowired
    private transient ExampleTaskRepository repository;

    @Test
    void shouldSaveAndLoadTask() {
        ExampleTask saved = repository.save(new ExampleTask(
            null,
            "ABC12345",
            "Task Title",
            "Task Description",
            "Task Status",
            LocalDateTime.now()
        ));

        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findById(saved.getId())).contains(saved);
    }
}
