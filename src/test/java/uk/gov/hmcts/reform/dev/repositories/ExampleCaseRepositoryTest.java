package uk.gov.hmcts.reform.dev.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uk.gov.hmcts.reform.dev.models.ExampleCase;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ExampleCaseRepositoryTest {

    @Autowired
    private transient ExampleCaseRepository repository;

    @Test
    void shouldSaveAndLoadCase() {
        ExampleCase saved = repository.save(new ExampleCase(
            null,
            "ABC12345",
            "Case Title",
            "Case Description",
            "Case Status",
            LocalDateTime.now()
        ));

        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findById(saved.getId())).contains(saved);
    }
}
