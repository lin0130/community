package lin.community.communtiy.mapper;

import lin.community.communtiy.model.Question;
import lin.community.communtiy.model.QuestionExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface QuestionEtcMapper {
    int incView(Question record);
}