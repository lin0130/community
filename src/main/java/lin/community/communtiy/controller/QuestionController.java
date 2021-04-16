package lin.community.communtiy.controller;

import lin.community.communtiy.dto.*;
import lin.community.communtiy.enums.CommentTypeEnum;
import lin.community.communtiy.exception.CustomizeErrorCode;
import lin.community.communtiy.model.Mylike;
import lin.community.communtiy.model.User;
import lin.community.communtiy.service.CommentService;
import lin.community.communtiy.service.NotificationService;
import lin.community.communtiy.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @ResponseBody
    @PostMapping("/ilike")
    public ResultDTO ilike(@RequestBody IlikeDTO ilikeDTO, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Mylike mylike = new Mylike();
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        mylike.setUserid(user.getId());
        mylike.setType(ilikeDTO.getType());
        mylike.setQuestionid(ilikeDTO.getQuestionId());

        int result = questionService.ilike(mylike);
        if (result == 0) {
            return ResultDTO.errorOf(2021, "您已经收藏了该问题");
        }
        return ResultDTO.okOf();
    }

    @GetMapping("/question/unlike/{id}")
    public String unlike(@PathVariable(name = "id") Long questionId,
                         HttpServletRequest request,
                         Model model) {
        if (questionId != null) {
            questionService.unlike(questionId);
        }
        User user = (User) request.getSession().getAttribute("user");
        List<Long> questions = questionService.iLikes(user.getId());
        PaginationDTO paginationDTO = questionService.list(questions, 1, 5);
        model.addAttribute("paginationDTO", paginationDTO);
        model.addAttribute("section", "ilike");
        model.addAttribute("sectionName", "我的收藏");
        return "profile";
    }

}
