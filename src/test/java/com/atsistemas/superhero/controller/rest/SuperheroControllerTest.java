package com.atsistemas.superhero.controller.rest;

import com.atsistemas.superhero.models.entity.Superhero;
import com.atsistemas.superhero.models.entity.User;
import com.atsistemas.superhero.models.service.ISuperheroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class SuperheroControllerTest {

    private ObjectMapper mapper = new ObjectMapper();
    public static final String HEADER_AUTHORIZATION_KEY = "Authorization";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldAuthorizeUserAdminAndReturnAllSuperheroes() throws Exception {
        User user = User.builder().user("admin").password("password1").build();

        final MvcResult mvcResult = getAuthorization(user);
        final MvcResult mvcResultFindAll = callApi(mvcResult.getResponse().getHeaderValues(HEADER_AUTHORIZATION_KEY), "/api/superheroes", "GET", null);

        assertThat(mvcResult.getResponse().getHeader(HEADER_AUTHORIZATION_KEY)).isNotBlank();
        assertThat(mvcResultFindAll.getResponse().getContentAsString()).isNotBlank();
        assertThat(mvcResultFindAll.getResponse().getContentAsString()).contains("Batman");
    }

    @Test
    void shouldAuthorizeUserNormalAndReturnAllSuperheroes() throws Exception {
        User user = User.builder().user("gerardo").password("password2").build();

        final MvcResult mvcResult = getAuthorization(user);
        final MvcResult mvcResultFindAll = callApi(mvcResult.getResponse().getHeaderValues(HEADER_AUTHORIZATION_KEY), "/api/superheroes", "GET", null);

        assertThat(mvcResult.getResponse().getHeader(HEADER_AUTHORIZATION_KEY)).isNotBlank();
        assertThat(mvcResultFindAll.getResponse().getContentAsString()).isNotBlank();
        assertThat(mvcResultFindAll.getResponse().getContentAsString()).contains("Batman");
    }

    @Test
    void shouldAuthorizeUserAdminAndReturnOneSuperhero() throws Exception {
        User user = User.builder().user("admin").password("password1").build();

        final MvcResult mvcResult = getAuthorization(user);
        final MvcResult mvcResultFindOne = callApi(mvcResult.getResponse().getHeaderValues(HEADER_AUTHORIZATION_KEY), "/api/superheroes/"+1, "GET", null);

        assertThat(mvcResult.getResponse().getHeader(HEADER_AUTHORIZATION_KEY)).isNotBlank();
        assertThat(mvcResultFindOne.getResponse().getContentAsString()).isNotBlank();
        assertThat(mvcResultFindOne.getResponse().getContentAsString()).contains("Spiderman");
    }

    @Test
    void shouldAuthorizeUserAdminAndReturnOneSuperheroByName() throws Exception {
        User user = User.builder().user("admin").password("password1").build();

        final MvcResult mvcResult = getAuthorization(user);
        final MvcResult mvcResultFindOne = callApi(mvcResult.getResponse().getHeaderValues(HEADER_AUTHORIZATION_KEY), "/api/superheroes/name/" + "spi", "GET", null);

        assertThat(mvcResult.getResponse().getHeader(HEADER_AUTHORIZATION_KEY)).isNotBlank();
        assertThat(mvcResultFindOne.getResponse().getContentAsString()).isNotBlank();
        assertThat(mvcResultFindOne.getResponse().getContentAsString()).contains("Spiderman");
    }

    @Test
    void shouldAuthorizeUserAdminAndDeleteOneSuperhero() throws Exception {
        User user = User.builder().user("admin").password("password1").build();

        final MvcResult mvcResult = getAuthorization(user);
        callApi(mvcResult.getResponse().getHeaderValues(HEADER_AUTHORIZATION_KEY), "/api/superheroes/"+3, "DELETE", null);
        final MvcResult mvcResultFindAll = callApi(mvcResult.getResponse().getHeaderValues(HEADER_AUTHORIZATION_KEY), "/api/superheroes", "GET", null);

        assertThat(mvcResult.getResponse().getHeader(HEADER_AUTHORIZATION_KEY)).isNotBlank();
        assertThat(mvcResultFindAll.getResponse().getContentAsString()).isNotBlank();
        assertThat(mvcResultFindAll.getResponse().getContentAsString()).doesNotContain("Ironman");
    }

    @Test
    void shouldAuthorizeUserAdminAndModifyOneSuperhero() throws Exception {
        User user = User.builder().user("admin").password("password1").build();

        final MvcResult mvcResult = getAuthorization(user);
        callApi(mvcResult.getResponse().getHeaderValues(HEADER_AUTHORIZATION_KEY), "/api/superheroes", "PUT",
                "{ \"id\": 4, \"name\": \"Captain America\", \"dateOfBirth\": \"2000-08-20\", \"country\": \"EEUU\" }");
        final MvcResult mvcResultFindOne = callApi(mvcResult.getResponse().getHeaderValues(HEADER_AUTHORIZATION_KEY), "/api/superheroes/"+4, "GET", null);

        assertThat(mvcResult.getResponse().getHeader(HEADER_AUTHORIZATION_KEY)).isNotBlank();
        assertThat(mvcResultFindOne.getResponse().getContentAsString()).isNotBlank();
        assertThat(mvcResultFindOne.getResponse().getContentAsString()).contains("Captain America");
    }

    private MvcResult getAuthorization(User user) throws Exception {
        return mockMvc.perform(
                post("/login").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(user)))
                .andDo(print()).andExpect(status().isOk()).andReturn();
    }

    private MvcResult callApi(Object authorization, String endPoint, String method, String content) throws Exception {
        switch (method) {
            case "GET":
                return mockMvc.perform(MockMvcRequestBuilders.get(endPoint)
                        .contentType(MediaType.APPLICATION_JSON).header(HEADER_AUTHORIZATION_KEY, authorization)).
                        andExpect(status().isOk()).andDo(print()).andReturn();
            case "POST":
                return mockMvc.perform(MockMvcRequestBuilders.post(endPoint)
                        .contentType(MediaType.APPLICATION_JSON).header(HEADER_AUTHORIZATION_KEY, authorization)).
                        andExpect(status().isOk()).andDo(print()).andReturn();
            case "DELETE":
                return mockMvc.perform(MockMvcRequestBuilders.delete(endPoint)
                        .contentType(MediaType.APPLICATION_JSON).header(HEADER_AUTHORIZATION_KEY, authorization)).
                        andExpect(status().isOk()).andDo(print()).andReturn();
            case "PUT":
                return mockMvc.perform(MockMvcRequestBuilders.put(endPoint)
                        .contentType(MediaType.APPLICATION_JSON).header(HEADER_AUTHORIZATION_KEY, authorization)
                        .content(content)).
                        andExpect(status().isOk()).andDo(print()).andReturn();
            default:
                return null;
        }
    }

}
