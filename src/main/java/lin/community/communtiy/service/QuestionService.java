package lin.community.communtiy.service;

import lin.community.communtiy.dto.QuestionDto;
import lin.community.communtiy.mapper.QuestionMapper;
import lin.community.communtiy.mapper.UserMapper;
import lin.community.communtiy.model.Question;
import lin.community.communtiy.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    public List<QuestionDto> list() {
        List<Question> questions = questionMapper.list();
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);//把question中的属性快速copy到questionDto中
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        return questionDtoList;
    }
}
