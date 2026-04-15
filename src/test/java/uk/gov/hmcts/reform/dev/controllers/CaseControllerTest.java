package uk.gov.hmcts.reform.dev.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CaseControllerTest {

    @Autowired
    private transient MockMvc mockMvc;

    @Test
    void shouldGetExampleCaseFromDatabase() throws Exception {
        mockMvc.perform(get("/get-example-case"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.caseNumber", is("ABC12345")))
            .andExpect(jsonPath("$.title", is("Case Title")));
    }
}
