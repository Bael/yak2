package ru.otus.spring.hw.kanban.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.otus.spring.hw.kanban.dto.StageDTO;
import ru.otus.spring.hw.kanban.exceptions.StageNotFoundException;
import ru.otus.spring.hw.kanban.service.StageService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class StageControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private StageService stageService;

    private JacksonTester<StageDTO> jsonStage;

    @Before
    public void setUp()  {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void getStages() throws Exception {
        List<StageDTO> list = new ArrayList<>();
        StageDTO stage = new StageDTO();
        stage.id = 1;
        stage.name = "plan";
        list.add(stage);

        StageDTO stage2 = new StageDTO();
        stage2.id = 2;
        stage2.name = "do";
        list.add(stage2);

        given(this.stageService.findAll())
                .willReturn(list);

        this.mvc.perform(MockMvcRequestBuilders.get("/stages").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(Matchers.containsString("plan")))
                .andExpect(content().string(Matchers.containsString("do")));

    }

    @Test
    public void getNonExistingStage() throws Exception {

        given(this.stageService.find(1))
                .willThrow(new StageNotFoundException("Stage with 1 not found."));

        this.mvc.perform(MockMvcRequestBuilders.get("/stages/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }


    @Test
    public void updateStage() throws Exception {
        int id = 1;
        StageDTO stageDTO = new StageDTO(id, "plan", "plan to do something", 1);


        given(this.stageService.find(id))
                .willReturn(stageDTO);

        StageDTO changedBoard = new StageDTO(id, "plan", "plan to do something fast", 1);

        given(this.stageService.update(changedBoard))
                .willReturn(changedBoard);


        this.mvc.perform(
                MockMvcRequestBuilders.put("/stages/" + id)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .content(jsonStage.write(changedBoard).getJson())
        )

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(Matchers.containsString("fast")));

    }


}