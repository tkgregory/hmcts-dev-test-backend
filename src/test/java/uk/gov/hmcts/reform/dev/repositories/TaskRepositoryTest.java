package uk.gov.hmcts.reform.dev.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.models.TaskStatus;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private transient TaskRepository repository;

    @Test
    void shouldSaveAndLoadTask() {
        Task saved = repository.save(new Task(
            null,
            "Task Title",
            "Task Description",
            TaskStatus.IN_PROGRESS,
            LocalDateTime.now().plusDays(2)
        ));

        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findById(saved.getId())).contains(saved);
    }
}
