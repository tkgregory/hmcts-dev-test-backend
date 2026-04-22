package uk.gov.hmcts.reform.dev.controllers.request;

import uk.gov.hmcts.reform.dev.models.TaskStatus;

public record UpdateTaskStatusRequest(TaskStatus status) {
}
