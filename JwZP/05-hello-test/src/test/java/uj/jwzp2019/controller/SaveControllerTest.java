package uj.jwzp2019.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uj.jwzp2019.model.Person;
import uj.jwzp2019.saver.Saver;
import uj.jwzp2019.service.PeopleService;
import uj.jwzp2019.utils.SystemUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
public class SaveControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PeopleService peopleService;

    @Mock
    private Saver saver;

    @Mock
    private SystemUtils systemUtils;

    @InjectMocks
    private SaveController saveController;

    @BeforeEach
    private void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(saveController)
                .setControllerAdvice(HelloTestHandler.class)
                .build();
    }

    @Test
    void saveToFilesSavesPersonForDefaultIdTest() throws Exception {
        // given
        Person han = new Person();
        han.setName("Han Solo");
        given(peopleService.getPersonById(1)).willReturn(han);
        // when
        MockHttpServletResponse response = mockMvc.perform(
                get("/save")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        // then
        Mockito.verify(saver, times(2)).saveList(Mockito.anyList(), Mockito.anyString());

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Data successfully saved");
    }

    @Test
    void saveToFilesSavesPersonWhenExistsTest() throws Exception {
        // given
        Person han = new Person();
        han.setName("Han Solo");
        given(peopleService.getPersonById(2)).willReturn(han);
        // when
        MockHttpServletResponse response = mockMvc.perform(
                get("/save?id=2")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        // then
        Mockito.verify(saver, times(2)).saveList(Mockito.anyList(), Mockito.anyString());

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Data successfully saved");
    }

    @Test
    void changePrefixTest() throws Exception {
        // given
        String prev = "/prev";
        String next = "next";
        ReflectionTestUtils.setField(saveController, "PREFIX", prev);
        // when
        MockHttpServletResponse response = mockMvc.perform(
                get("/prefix?prefix=" + next)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(ReflectionTestUtils.getField(saveController, "PREFIX")).isEqualTo("/" + next);
    }
}
