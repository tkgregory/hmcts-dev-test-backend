package uk.gov.hmcts.reform.dev.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.models.TaskStatus;
import uk.gov.hmcts.reform.dev.repositories.TaskRepository;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TaskControllerTest {

    @Autowired
    private transient MockMvc mockMvc;

    @Autowired
    private transient TaskRepository taskRepository;

    @Test
    void shouldGetAllTasksFromDatabase() throws Exception {
        taskRepository.save(new Task(
            null,
            "Second task",
            "Another task in the list",
            TaskStatus.IN_PROGRESS,
            LocalDateTime.now().plusDays(2)
        ));

        mockMvc.perform(get("/tasks"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title", is("Task Title")))
            .andExpect(jsonPath("$[1].title", is("Second task")));
    }

    @Test
    void shouldGetSingleTaskById() throws Exception {
        Task task = taskRepository.save(new Task(
            null,
            "Single task lookup",
            "Task for get by id test",
            TaskStatus.IN_PROGRESS,
            LocalDateTime.now().plusDays(1)
        ));

        mockMvc.perform(get("/tasks/" + task.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(task.getId())))
            .andExpect(jsonPath("$.title", is("Single task lookup")));
    }

    @Test
    void shouldReturnNotFoundWhenTaskByIdDoesNotExist() throws Exception {
        mockMvc.perform(get("/tasks/999"))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateTask() throws Exception {
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "title": "Prepare hearing bundle",
                      "description": "Collect and verify documents",
                      "status": "IN_PROGRESS",
                      "dueDateTime": "2026-05-01T10:30:00"
                    }
                    """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.title", is("Prepare hearing bundle")))
            .andExpect(jsonPath("$.description", is("Collect and verify documents")))
            .andExpect(jsonPath("$.status", is("IN_PROGRESS")))
            .andExpect(jsonPath("$.dueDateTime", is("2026-05-01T10:30:00")));
    }

    @Test
    void shouldCreateTaskWithoutDescription() throws Exception {
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "title": "Review evidence",
                      "status": "IN_PROGRESS",
                      "dueDateTime": "2026-05-02T09:00:00"
                    }
                    """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.title", is("Review evidence")))
            .andExpect(jsonPath("$.description").doesNotExist())
            .andExpect(jsonPath("$.status", is("IN_PROGRESS")))
            .andExpect(jsonPath("$.dueDateTime", is("2026-05-02T09:00:00")));
    }

    @Test
    void shouldRejectTaskCreationWhenRequiredFieldsMissing() throws Exception {
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "description": "Missing title and due date",
                      "status": "IN_PROGRESS"
                    }
                    """))
            .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectTaskCreationWhenStatusIsNotSupported() throws Exception {
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "title": "Review evidence",
                      "status": "OPEN",
                      "dueDateTime": "2026-05-02T09:00:00"
                    }
                    """))
            .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateTaskStatus() throws Exception {
        Task task = taskRepository.save(new Task(
            null,
            "Status update task",
            "Task for update test",
            TaskStatus.IN_PROGRESS,
            LocalDateTime.now().plusDays(1)
        ));

        mockMvc.perform(patch("/tasks/" + task.getId() + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "status": "COMPLETED"
                    }
                    """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(task.getId())))
            .andExpect(jsonPath("$.status", is("COMPLETED")))
            .andExpect(jsonPath("$.title", is("Status update task")));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingMissingTaskStatus() throws Exception {
        mockMvc.perform(patch("/tasks/999/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "status": "COMPLETED"
                    }
                    """))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldRejectStatusUpdateWhenStatusMissing() throws Exception {
        Task task = taskRepository.save(new Task(
            null,
            "Blank status task",
            "Task for bad request test",
            TaskStatus.IN_PROGRESS,
            LocalDateTime.now().plusDays(1)
        ));

        mockMvc.perform(patch("/tasks/" + task.getId() + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "status": " "
                    }
                    """))
            .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteTask() throws Exception {
        Task task = taskRepository.save(new Task(
            null,
            "Delete task",
            "Task for delete test",
            TaskStatus.IN_PROGRESS,
            LocalDateTime.now().plusDays(1)
        ));

        mockMvc.perform(delete("/tasks/" + task.getId()))
            .andExpect(status().isNoContent());

        mockMvc.perform(get("/tasks/" + task.getId()))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingMissingTask() throws Exception {
        mockMvc.perform(delete("/tasks/999"))
            .andExpect(status().isNotFound());
    }
}
