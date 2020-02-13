package lin.community.communtiy.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001, "你要找的问题不存在"),
    TARGET_PARAM_NOT_FOUND(2002, "未选择任何评论或问题进行回复"),
    NO_LOGIN(2003,"当前操作需要登录，请重新登陆后重试"),
    SYSTEM_ERROR(2004,"服务器出错啦"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在"),
    CONTENT_IS_EMPTY(2007,"回复内容不能为空"),
    READ_NOTIFICATION_FAIL(2008,"兄弟，你只是读别人信息呢？"),
    NOTIFICATION_NOT_FOUND(2009,"消息不翼而飞了")
    ;
    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(String message) {
        this.message = message;
    }


}
