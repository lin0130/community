package lin.community.communtiy.dto;

import lin.community.communtiy.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentor;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer likeCount;
    private Integer commentCount;
    private String content;
    private User user;

}
