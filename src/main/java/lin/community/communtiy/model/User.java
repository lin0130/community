package lin.community.communtiy.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String accountId;
    private String name;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarurl;

}
