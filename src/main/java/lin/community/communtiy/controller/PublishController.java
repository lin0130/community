package lin.community.communtiy.controller;

import lin.community.communtiy.dto.QuestionDTO;
import lin.community.communtiy.model.Question;
import lin.community.communtiy.model.User;
import lin.community.communtiy.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    String edit(@PathVariable (name = "id") Integer id,
                Model model)
    {
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id",question.getId());
        return "publish";
    }

    @GetMapping("/publish")
    String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam("id") Integer id,
            HttpServletRequest servletRequest,
            Model model
    ) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        if (title != null && title == "") {
            model.addAttribute("error", "标题不能为空!");
            return "publish";
        }
        if (description != null && description == "") {
            model.addAttribute("error", "内容不能为空!");
            return "publish";
        }
        if (tag != null && tag == "") {
            model.addAttribute("error", "标签不能为空!");
            return "publish";
        }
        User user ;
        user = (User) servletRequest.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setTag(tag);
        question.setDescription(description);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
