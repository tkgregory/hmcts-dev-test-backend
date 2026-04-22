package uk.gov.hmcts.reform.dev.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.models.TaskStatus;
import uk.gov.hmcts.reform.dev.repositories.TaskRepository;

import java.time.LocalDateTime;

@Component
public class ExampleTaskSeeder implements CommandLineRunner {

    private final TaskRepository repository;

    public ExampleTaskSeeder(final TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(final String... args) {
        if (repository.count() == 0) {
            repository.save(new Task(
                null,
                "Task Title",
                "Task Description",
                TaskStatus.IN_PROGRESS,
                LocalDateTime.now().plusDays(7)
            ));
        }
    }
}
