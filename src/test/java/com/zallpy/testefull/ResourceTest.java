/**
 * @author benhurmarques
 * 
 * Feb 6, 2021
 */
package com.zallpy.testefull;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.google.gson.JsonObject;

import junit.framework.TestCase;

/**
 * @author benhurmarques
 *
 */

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ResourceTest extends TestCase {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testLogin() {

		try {

			String username = "admin@email.com";
			String password = "admin";

			JsonObject json = new JsonObject();
			json.addProperty("username", username);
			json.addProperty("password", password);

			String body = json.toString();

			MvcResult result = mockMvc
					.perform(post("/authenticate").accept(MediaType.APPLICATION_JSON)
							.contentType(MediaType.APPLICATION_JSON).content(body))
					.andExpect(status().isOk()).andReturn();

			String response = result.getResponse().getContentAsString();
			response = response.replace("{\"token\": \"", "");
			String token = response.replace("\"}", "");

		} catch (Exception e) {

		}

	}

}
