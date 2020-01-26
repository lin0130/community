package lin.community.communtiy.dto;

import lin.community.communtiy.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtcreate;
    private Long gmtmodified;
    private Integer creator;
    private Integer commentcount;
    private Integer likecount;
    private Integer viewcount;
    private User user;
}
