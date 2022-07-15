package qna.study;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qna.domain.Question;
import qna.service.QnaService;

@RestController
public class TestConfigurationController {

    private final QnaService qnaService;

    public TestConfigurationController(final QnaService qnaService) {
        this.qnaService = qnaService;
    }

    @PostMapping("/api/test")
    public ResponseEntity<Question> login(@RequestParam final Long id) {
        return ResponseEntity.ok().body(qnaService.findQuestionById(id));
    }
}
