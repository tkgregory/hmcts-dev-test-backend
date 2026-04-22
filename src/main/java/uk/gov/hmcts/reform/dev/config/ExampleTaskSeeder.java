package uk.gov.hmcts.reform.dev.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.dev.models.ExampleTask;
import uk.gov.hmcts.reform.dev.repositories.ExampleTaskRepository;

import java.time.LocalDateTime;

@Component
public class ExampleTaskSeeder implements CommandLineRunner {

    private final ExampleTaskRepository repository;

    public ExampleTaskSeeder(final ExampleTaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(final String... args) {
        if (repository.count() == 0) {
            repository.save(new ExampleTask(
                null,
                "ABC12345",
                "Task Title",
                "Task Description",
                "Task Status",
                LocalDateTime.now()
            ));
        }
    }
}
