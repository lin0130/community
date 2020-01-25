package lin.community.communtiy.model;

import lombok.Data;

@Data
public class Question {
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


}
