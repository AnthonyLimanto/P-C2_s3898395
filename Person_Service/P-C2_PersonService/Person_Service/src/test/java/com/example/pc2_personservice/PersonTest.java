package com.example.pc2_personservice;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonTest {

    @Autowired
    private MockMvc mockMvc;
    MvcResult request;
    String content;
    String test_account_1, test_account_2;

    @BeforeEach
    void setUp() throws Exception {
        test_account_1 = "{\"account_name\":\"J_Smith\",\"acc_num\":\"23456789\",\"type\":\"Loan\",\"balance\": \"300\", \"date\" : \"2022-10-11\"}";
        test_account_2 = "{\"account_name\":\"Anthony Limanto\",\"acc_num\":\"1123456789\",\"type\":\"Term_Investment\",\"balance\": \"800\", \"date\" : \"2022-10-11\"}";

        // Add the doctors to the JPA
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/account")
                .content(test_account_1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/account")
                .content(test_account_2)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    @Test
    void getAllAccountRequest() throws Exception {
        // Expect both accounts to be returned
        String expected_string = "[" + test_account_1 + "," + test_account_2 + "]";

        // get all request on endpoint
        request = this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/accounts/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // Return the content of the request
                .andReturn();

        // JSON of what is returned
        content = request.getResponse().getContentAsString();
        assertEquals(content, expected_string);
    }

    @Test
    void addingAccountToDBRequest() throws Exception {
        // New doctor to add
        final String new_Account = "{\"account_name\":\"Abe Paul\",\"acc_num\":\"14545489\",\"type\":\"Term_Investment\",\"balance\": \"8010\", \"date\" : \"2022-10-11\"}";
        request = this.mockMvc.perform(MockMvcRequestBuilders
                        // Post request on the endpoint
                        .post("/account")
                        .content(new_Account)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        content = request.getResponse().getContentAsString();

        // Assert that the returned doctor is the new doctor
        assertEquals(content, new_Account);
    }

    @Test
    void getSpecificAccountRequest() throws Exception {
        // Request a get that returns a specific account
        request = this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/account/account1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        content = request.getResponse().getContentAsString();

        // Expect that the content returned is the test_account_1 that was added in above
        assertEquals(test_account_1,content);
    }


}
