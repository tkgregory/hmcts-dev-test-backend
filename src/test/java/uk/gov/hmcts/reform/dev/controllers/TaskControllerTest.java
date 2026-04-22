package uk.gov.hmcts.reform.dev.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private transient MockMvc mockMvc;

    @Test
    void shouldGetFirstTaskFromDatabase() throws Exception {
        mockMvc.perform(get("/tasks"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title", is("Task Title")));
    }

    @Test
    void shouldGetSingleTaskById() throws Exception {
        mockMvc.perform(get("/tasks/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.title", is("Task Title")));
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
                      "status": "OPEN",
                      "dueDateTime": "2026-05-01T10:30:00"
                    }
                    """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.title", is("Prepare hearing bundle")))
            .andExpect(jsonPath("$.description", is("Collect and verify documents")))
            .andExpect(jsonPath("$.status", is("OPEN")))
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
                      "status": "OPEN"
                    }
                    """))
            .andExpect(status().isBadRequest());
    }
}
