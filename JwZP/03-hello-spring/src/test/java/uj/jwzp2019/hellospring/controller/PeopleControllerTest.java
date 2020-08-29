package uj.jwzp2019.hellospring.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uj.jwzp2019.hellospring.model.Person;
import uj.jwzp2019.hellospring.service.PeopleService;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
class PeopleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PeopleService peopleService;

    @InjectMocks
    private PeopleController peopleController;

    @BeforeEach
    private void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(peopleController)
                .setControllerAdvice(IllegalArgumentHandler.class)
                .build();
    }

    @Test
    void getPersonByIdReturnsPersonForDefaultId() throws Exception {
        // given
        Person han = new Person();
        han.setName("Han Solo");
        given(peopleService.getPersonById(1)).willReturn(han);
        // when
        MockHttpServletResponse response = mockMvc.perform(
                get("/person/")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Han Solo");
    }

    @Test
    void getPersonByIdReturnsPersonWhenExists() throws Exception {
        // given
        Person han = new Person();
        han.setName("Han Solo");
        given(peopleService.getPersonById(2)).willReturn(han);
        // when
        MockHttpServletResponse response = mockMvc.perform(
                get("/person?id=2")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Han Solo");
    }

    @Test
    void getPeopleInRangeWithMatchingColorReturnsPeopleMatching() throws Exception {
        // given
        Person han = new Person();
        han.setName("Han Solo");
        given(peopleService.getPeopleInRangeWithEyeColor(1, 3, "brown")).willReturn(List.of(han));
        // when
        MockHttpServletResponse response = mockMvc.perform(
                get("/person/eye")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Han Solo");
    }

    @Test
    void getPeopleInRangeWithMatchingColorReturnsEmptyListIfNobodyMatches() throws Exception {
        // given
        given(peopleService.getPeopleInRangeWithEyeColor(1, 3, "brown")).willReturn(emptyList());
        // when
        MockHttpServletResponse response = mockMvc.perform(
                get("/person/eye")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void getPeopleInRangeWithMatchingColorReturnsBadRequestIfFromIdGreaterThanToId() throws Exception {
        // given
        String params = "fromId=3&toId=1";
        // when
        MockHttpServletResponse response = mockMvc.perform(
                get("/person/eye?" + params)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo("FromId cannot be greater than toId");
    }
}