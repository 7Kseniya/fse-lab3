package se.ifmo.lab4;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.hamcrest.Matchers.is;

import se.ifmo.lab4.service.implementation.ElementServiceImplementation;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class Lab4ApplicationTests {



	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	
	@Test
	void loginTest() throws Exception {
		String baseUrl = "/api/v1/authentication/register";  
		String requestBody = "{\"login\":\"test\",\"password\":\"test\"}";
	
		mockMvc.perform(MockMvcRequestBuilders.post(baseUrl)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(MockMvcResultMatchers.status().isOk())
			    .andExpect(MockMvcResultMatchers.jsonPath("$.status", is("CREATED")));


	}
}

