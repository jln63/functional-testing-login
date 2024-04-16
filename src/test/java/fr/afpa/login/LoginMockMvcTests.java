package fr.afpa.login;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Classe permettant de tester la page de login de l'application.
 * La classe utilise MockMvc qui permet de tester la gestion des requêtes http de l'application
 * sans DEMARRER DE SERVEUR HTTP.
 */
@SpringBootTest
@AutoConfigureMockMvc
class LoginMockMvcTests {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	void loginWithValidUserThenAuthenticated() throws Exception {
		FormLoginRequestBuilder login = formLogin()
			.user("ada_lovelace")
			.password("supersecured");
		mockMvc.perform(login).andExpect(authenticated().withUsername("ada_lovelace"));
	}

	@Test
	void loginWithInvalidUserThenUnauthenticated() throws Exception {
		FormLoginRequestBuilder login = formLogin()
			.user("attacker")
			.password("unsecuredpassword");

		mockMvc.perform(login).andExpect(unauthenticated());
	}

	@Test
	void accessUnsecuredResourceThenOk() throws Exception {
		mockMvc.perform(get("/css/common.css"))
			.andExpect(status().isOk());
	}

	@Test
	void accessSecuredResourceUnauthenticatedThenRedirectsToLogin() throws Exception {
		mockMvc.perform(get("/home"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrlPattern("**/login"));
	}

	@Test
	@WithMockUser
	void accessSecuredResourceAuthenticatedThenOk() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/home"))
				.andExpect(status().isOk())
				.andReturn();

		assertThat(mvcResult.getResponse().getContentAsString()).contains("Bonjour user !");
	}
}
