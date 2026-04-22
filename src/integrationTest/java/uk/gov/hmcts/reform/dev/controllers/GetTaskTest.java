package uk.gov.hmcts.reform.dev.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.models.TaskStatus;
import uk.gov.hmcts.reform.dev.repositories.TaskRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@Import(GetTaskTest.TestConfig.class)
class GetTaskTest {

    @Autowired
    private transient MockMvc mockMvc;

    @Autowired
    private transient TaskRepository taskRepository;

    @DisplayName("Should return a task upon tasks request with 200 response code")
    @Test
    void getTaskEndpoint() throws Exception {
        given(taskRepository.findFirstByOrderByIdAsc()).willReturn(Optional.of(new Task(
            1,
            "Task Title",
            "Task Description",
            TaskStatus.IN_PROGRESS,
            LocalDateTime.parse("2026-05-01T10:30:00")
        )));

        MvcResult response = mockMvc.perform(get("/tasks")).andExpect(status().isOk()).andReturn();

        assertThat(response.getResponse().getContentAsString()).contains("Task Title");
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        TaskRepository taskRepository() {
            return mock(TaskRepository.class);
        }
    }
}
