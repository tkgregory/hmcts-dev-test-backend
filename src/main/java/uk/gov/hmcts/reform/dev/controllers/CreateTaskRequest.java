package uk.gov.hmcts.reform.dev.controllers;

import java.time.LocalDateTime;

import uk.gov.hmcts.reform.dev.models.TaskStatus;

public record CreateTaskRequest(
    String title,
    String description,
    TaskStatus status,
    LocalDateTime dueDateTime
) {
}
