package uk.gov.hmcts.reform.dev.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.dev.models.ExampleTask;
import uk.gov.hmcts.reform.dev.repositories.ExampleTaskRepository;

@RestController
public class TaskController {

    private final ExampleTaskRepository exampleTaskRepository;

    public TaskController(final ExampleTaskRepository exampleTaskRepository) {
        this.exampleTaskRepository = exampleTaskRepository;
    }

    @GetMapping(value = "/get-example-task", produces = "application/json")
    public ResponseEntity<ExampleTask> getExampleTask() {
        return exampleTaskRepository.findFirstByOrderByIdAsc()
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
