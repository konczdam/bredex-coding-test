package hu.konczdam.forma1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.konczdam.forma1.model.Formula1Team;
import hu.konczdam.forma1.service.Formula1TeamService;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Year;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
public class Formula1ControllerTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private Formula1TeamService formula1TeamService;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mvc;

    private Formula1Team formula1Team = new Formula1Team(1, "Ferrari", Year.of(1950), 5, true);

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        when(formula1TeamService.getAllTeams()).thenReturn(List.of(formula1Team));
        when(formula1TeamService.getTeamById(1)).thenReturn(Optional.ofNullable(formula1Team));
    }

    @Test
    @WithAnonymousUser
    public void whenAnonymousListTeams_thenOk() throws Exception {
        mvc.perform(get("/teams"))
                .andExpect(status().isOk());
        verify(formula1TeamService).getAllTeams();
    }

    @Test
    @WithAnonymousUser
    public void whenAnonymousFetchesTeam_thenOk() throws Exception {
        mvc.perform(get("/teams/1"))
                .andExpect(status().isOk());
        verify(formula1TeamService).getTeamById(1);
    }

    @Test
    @WithAnonymousUser
    public void whenAnonymousFetchesTeam_thenNotFound() throws Exception {
        mvc.perform(get("/teams/2"))
                .andExpect(status().isNotFound());
        verify(formula1TeamService).getTeamById(2);
    }

    @Test
    @WithAnonymousUser
    public void whenAnonymousDeletesTeam_thenForbidden() throws Exception {
        mvc.perform(delete("/teams/1"))
                .andExpect(status().isForbidden());
        verify(formula1TeamService, times(0)).deleteTeam(1);
    }

    @Test
    @WithUserDetails("admin")
    public void whenUserDeletesTeam_thenOk() throws Exception {
        mvc.perform(delete("/teams/1"))
                .andExpect(status().isNoContent());
        verify(formula1TeamService).deleteTeam(1);
    }

    @Test
    @WithAnonymousUser
    public void whenAnonymousEditsTeam_thenForbidden() throws Exception {
        mvc.perform(put("/teams/1"))
                .andExpect(status().isForbidden());
        verify(formula1TeamService, times(0)).saveTeam(any());
    }

    @Test
    @WithUserDetails("admin")
    public void whenUserEditsTeam_thenOk() throws Exception {
        Formula1Team body = new Formula1Team(1, "Ferrari", Year.of(1945), 5, true);
        mvc.perform(
                        put("/teams/1", body)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(body))
                )
                .andExpect(status().isOk());

        verify(formula1TeamService).saveTeam(body);
    }
}
