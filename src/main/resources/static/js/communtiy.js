function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment-content").val();
    $.ajax({
        type:"post",
        url:"/comment",
        contentType:'application/json',
        data:JSON.stringify({
            "parentId":questionId,
            "content":content,
            "type":1
        }),
        success:function (response) {
            if (response.code==200)
            {
                $("#comment_section").hide();
            }else
            {
                if (response.code == 2003)
                {
                    var isAccepted = confirm(response.message);
                    if(isAccepted)
                    {
                        window.open("https://github.com/login/oauth/authorize?client_id=1aedee186d31295e9563&redirect_uri=http://localhost:8082/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);
                    }
                }else
                {
                    alert(response.message);
                }
            }
            console.log(response);
        },
        dataType:"json"
    });
    // console.log(questionId);
    // console.log(content);
}