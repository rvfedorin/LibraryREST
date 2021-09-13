package org.frv.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import org.frv.config.DataConfigForTest;
import org.frv.config.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Roman V. Fedorin
 */
@ActiveProfiles("test")
@SpringJUnitWebConfig({DataConfigForTest.class, SecurityConfig.class})
public class AuthenticationRestControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @SneakyThrows
    public void loginWithGoodUser() {

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("username", "rf");
        node.put("password", "test");

        mvc
                .perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(node.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"username\":\"rf\"")));
    }

    @Test
    @SneakyThrows
    public void loginWithBadUser() {

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("username", "noname");
        node.put("password", "nopass");

        mvc
                .perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(node.toString()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @SneakyThrows
    public void loginWithBadRequest() {

        mvc
                .perform(post("/auth/login"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    public void logoutTest() {
        mvc
                .perform(post("/auth/logout")
                        .with(user("admin").password("pass").roles("ADMIN")))
                .andExpect(status().isOk());
    }
}