package qna.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import qna.domain.Question;
import qna.domain.UserTest;
import qna.service.QnaService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TestConfigurationController.class)
public class ConfigurationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QnaService qnaService;

    @DisplayName("Application에 @EnableJpaAuditing을 선언하면 @WebMvcTest로 돌리면 에러가 발생하므로, @Configuration 클래스로 분리")
    @Test
    void getToken() throws Exception {
        given(qnaService.findQuestionById(1L)).willReturn(new Question("title", "content", UserTest.JAVAJIGI));

        mockMvc.perform(post("/api/test")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
