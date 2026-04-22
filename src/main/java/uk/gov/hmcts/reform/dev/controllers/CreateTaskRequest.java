package uk.gov.hmcts.reform.dev.controllers;

import java.time.LocalDateTime;

public record CreateTaskRequest(
    String title,
    String description,
    String status,
    LocalDateTime dueDateTime
) {
}
