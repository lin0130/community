package lin.community.communtiy.controller;

import lin.community.communtiy.dto.CommentDTO;
import lin.community.communtiy.dto.PaginationDTO;
import lin.community.communtiy.dto.QuestionDTO;
import lin.community.communtiy.enums.CommentTypeEnum;
import lin.community.communtiy.model.User;
import lin.community.communtiy.service.CommentService;
import lin.community.communtiy.service.NotificationService;
import lin.community.communtiy.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        questionService.inView(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", commentDTOS);
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";
    }

    @GetMapping("/question/delete/{id}")
    public String deleteQuestion(@PathVariable(name = "id") Long id,
                                 HttpServletRequest servletRequest,
                                 Model model) {
        questionService.deleteQuestionById(id);
        User user = (User) servletRequest.getSession().getAttribute("user");
        PaginationDTO paginationDTO = notificationService.list(user.getId(), 1, 5);
        model.addAttribute("paginationDTO", paginationDTO);
        return "profile";
    }

}
