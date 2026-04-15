package uk.gov.hmcts.reform.dev.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.dev.models.ExampleCase;
import uk.gov.hmcts.reform.dev.repositories.ExampleCaseRepository;

import java.time.LocalDateTime;

@Component
public class ExampleCaseSeeder implements CommandLineRunner {

    private final ExampleCaseRepository repository;

    public ExampleCaseSeeder(final ExampleCaseRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(final String... args) {
        if (repository.count() == 0) {
            repository.save(new ExampleCase(
                null,
                "ABC12345",
                "Case Title",
                "Case Description",
                "Case Status",
                LocalDateTime.now()
            ));
        }
    }
}
