package lin.community.communtiy.dto;

import lombok.Data;

@Data
public class QuestionQueryDTO {
    private String search;
    private Integer page;
    private Integer size;
    private String tag;
    private String sort;
    private Long time;
}
