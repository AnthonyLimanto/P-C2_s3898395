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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonTest {

    @Autowired
    private MockMvc mockMvc;
    MvcResult request;
    String content;
    String test_person_1, test_person_2;

    @BeforeEach
    void setUp() throws Exception {
        test_person_1 = "{\"id\":1,\"name\":Anthony,\"address\":34 Melbourne,\"postcode\":3000,\"age\":25,\"job\":Engineer,\"email\":aaaa@gmail.com,\"phoneno\":12343567}";
        test_person_2 = "{\"id\":2,\"name\":Brian,\"address\":123 Street,\"postcode\":3001,\"age\":60,\"job\":Janitor,\"email\":bbbb@gmail.com,\"phoneno\":987654321}";

        // Add the persons to the JPA
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/person")
                .content(test_person_1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/person")
                .content(test_person_2)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    @Test
    void getAllPersonsRequest() throws Exception {
        // Expect both doctors to be returned
        String expected_string = "[" + test_person_1 + "," + test_person_2 + "]";

        // Perform a get all request from the endpoint
        request = this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/persons/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // Return the content of the request
                .andReturn();

        // Get the raw JSON of what is returned
        content = request.getResponse().getContentAsString();
        assertNotNull(content);
    }

    @Test
    void addingPersonToDBRequest() throws Exception {
        // New person to add
        final String new_Person = "{\"id\":3,\"name\":Abe,\"address\":123345 Street,\"postcode\":3201,\"age\":67,\"job\":Cashier,\"email\":bbaaaaabb@gmail.com,\"phoneno\":98127654321}";
        request = this.mockMvc.perform(MockMvcRequestBuilders
                        // Post request on the endpoint
                        .post("/person")
                        .content(new_Person)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        content = request.getResponse().getContentAsString();

        // Assert that the returned person is the new person
        assertEquals(content, new_Person);
    }

    @Test
    void getSpecificPersonRequest() throws Exception {
        // Request a get that returns a specific person
        request = this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/person/person1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        content = request.getResponse().getContentAsString();

        // Expect that the content returned is the test_person_1 that was added in above
        assertEquals(test_person_1,content);
    }


}
