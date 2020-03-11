package com.wesley.bitcointracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BitcoinControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldReturnLatestPrice() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/price/latest")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.price", is(2233.33)))
				.andExpect(jsonPath("$.lastUpdated", is("2012-01-01 02:02:02")));
	}

	@Test
	public void shouldReturnHistoricalPrices() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/price/historical")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].price", is(1233.33)))
				.andExpect(jsonPath("$[0].lastUpdated", is("2012-01-02 02:02:02")))
				.andExpect(jsonPath("$[1].price", is(900.33)))
				.andExpect(jsonPath("$[1].lastUpdated", is("2012-01-03 02:02:02")));
	}

}
