package uk.gov.hmcts.reform.dev.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.dev.models.ExampleCase;
import uk.gov.hmcts.reform.dev.repositories.ExampleCaseRepository;

@RestController
public class CaseController {

    private final ExampleCaseRepository exampleCaseRepository;

    public CaseController(final ExampleCaseRepository exampleCaseRepository) {
        this.exampleCaseRepository = exampleCaseRepository;
    }

    @GetMapping(value = "/get-example-case", produces = "application/json")
    public ResponseEntity<ExampleCase> getExampleCase() {
        return exampleCaseRepository.findFirstByOrderByIdAsc()
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
