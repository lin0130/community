package lin.community.communtiy.schedule;

import lin.community.communtiy.Cache.HotTagCache;
import lin.community.communtiy.mapper.QuestionMapper;
import lin.community.communtiy.model.Question;
import lin.community.communtiy.model.QuestionExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class HotTagSchedule {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private HotTagCache hotTagCache;

    @Scheduled(fixedRate = 1000 * 60 * 60 * 2)
    public void hotTagTask() {
        int offset = 0;
        int limit = 20;
        Map<String, Integer> prioritys = new HashMap<>();
        List<Question> questions = new ArrayList<>();
        while (offset == 0 || questions.size() == limit) {
            questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, limit));
            for (Question question : questions) {
                String[] tags = StringUtils.split(question.getTag(), ",");
                for (String tag : tags) {
                    Integer priority = prioritys.get(tag);
                    if (priority != null) {
                        prioritys.put(tag, priority + 5 + question.getViewCount() + question.getCommentCount());
                    } else {
                        prioritys.put(tag, 5 + question.getViewCount() + question.getCommentCount());
                    }
                }
            }
            offset += limit;
        }
        hotTagCache.updateTags(prioritys);
        prioritys.forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });

    }

}
