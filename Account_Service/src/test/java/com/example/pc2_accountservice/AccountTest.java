package com.example.pc2_accountservice;


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
public class AccountTest {

    @Autowired
    private MockMvc mockMvc;
    MvcResult request;
    String content;
    String test_account_1, test_account_2;

    @BeforeEach
    void setUp() throws Exception {
        test_account_1 = "{\"id\":1,\"type\":\"Loan\",\"acc_num\":\"23456789\",\"account_name\":\"J_Smith\",\"balance\":300.0,\"date\":\"2022-10-11T00:00:00.000+00:00\"}";
        test_account_2 = "{\"id\":2,\"type\":\"Term_Investment\",\"acc_num\":\"1123456789\",\"account_name\":\"Anthony Limanto\",\"balance\":800.0,\"date\":\"2022-10-11T00:00:00.000+00:00\"}";

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
    void deleteAccountRequest() throws Exception {
        request = this.mockMvc.perform(MockMvcRequestBuilders
                        // Delete request on the mock
                        .delete("/account/account2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        content = request.getResponse().getContentAsString();
        // Assert that the request has gone through and nothing is displayed
        assertEquals("", content);
    }

    @Test
    void addingAccountToDBRequest() throws Exception {

        // New doctor to add
        final String new_Account = "{\"id\":3,\"type\":\"Term_Investment\",\"acc_num\":\"14545489\",\"account_name\":\"Abe Paul\",\"balance\":8010.0,\"date\":\"2022-10-11T00:00:00.000+00:00\"}";
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
        assertEquals(new_Account, content);
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
