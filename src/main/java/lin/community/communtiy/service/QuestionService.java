package lin.community.communtiy.service;

import lin.community.communtiy.dto.PaginationDTO;
import lin.community.communtiy.dto.QuestionDTO;
import lin.community.communtiy.exception.CustomizeErrorCode;
import lin.community.communtiy.exception.CustomizeException;
import lin.community.communtiy.mapper.QuestionExtMapper;
import lin.community.communtiy.mapper.QuestionMapper;
import lin.community.communtiy.mapper.UserMapper;
import lin.community.communtiy.model.Question;
import lin.community.communtiy.model.QuestionExample;
import lin.community.communtiy.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionExtMapper questionEtcMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page >totalPage) {
            page =totalPage;
        }
        paginationDTO.setPagination(totalPage, page);

        Integer offset = size * (page - 1);

        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));
        List<QuestionDTO> questionDtoList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDto = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDto);//把question中的属性快速copy到questionDto中
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }

        paginationDTO.setQuestionDtos(questionDtoList);
        return paginationDTO;
    }

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(questionExample);
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page >totalPage) {
            page =totalPage;
        }
        paginationDTO.setPagination(totalPage, page);

        Integer offset = size * (page - 1);

        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        List<QuestionDTO> questionDtoList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDto = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDto);//把question中的属性快速copy到questionDto中
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }

        paginationDTO.setQuestionDtos(questionDtoList);
        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question==null)
        {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId()==null)
        {
//            question.setId(10000l);
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
            questionMapper.insert(question);
        }else
        {
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            int update = questionMapper.updateByExampleSelective(updateQuestion, example);
            if (update !=1)
            {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

        }
    }

    public void inView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionEtcMapper.incView(question);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if (StringUtils.isBlank(queryDTO.getTag()))
        {
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionEtcMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q ->{
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q,questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
