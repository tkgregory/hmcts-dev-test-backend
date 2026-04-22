package uk.gov.hmcts.reform.dev.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.dev.controllers.request.CreateTaskRequest;
import uk.gov.hmcts.reform.dev.controllers.request.UpdateTaskStatusRequest;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.repositories.TaskRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(final TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping(value = "/tasks", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> getExampleTask() {
        return taskRepository.findFirstByOrderByIdAsc()
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/tasks/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> getTaskById(@PathVariable final Integer id) {
        return taskRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/tasks", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> createTask(@RequestBody final CreateTaskRequest request) {
        if (isBlank(request.title()) || request.status() == null || request.dueDateTime() == null) {
            return ResponseEntity.badRequest().build();
        }

        Task task = new Task(
            null,
            request.title(),
            request.description(),
            request.status(),
            request.dueDateTime()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(taskRepository.save(task));
    }

    @PatchMapping(value = "/tasks/{id}/status", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> updateTaskStatus(
        @PathVariable final Integer id,
        @RequestBody final UpdateTaskStatusRequest request
    ) {
        if (request.status() == null) {
            return ResponseEntity.badRequest().build();
        }

        return taskRepository.findById(id)
            .map(task -> {
                task.setStatus(request.status());
                return ResponseEntity.ok(taskRepository.save(task));
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable final Integer id) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        taskRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private boolean isBlank(final String value) {
        return value == null || value.isBlank();
    }
}
