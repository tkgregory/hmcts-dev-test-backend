package uk.gov.hmcts.reform.dev.controllers.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

import uk.gov.hmcts.reform.dev.models.TaskStatus;

public record CreateTaskRequest(
    @NotBlank String title,
    String description,
    @NotNull TaskStatus status,
    @NotNull LocalDateTime dueDateTime
) {
}
