package lin.community.communtiy.service;

import lin.community.communtiy.mapper.UserMapper;
import lin.community.communtiy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void CreateOrUpdate(User user) {
       User dbUser = userMapper.findByAccountId(user.getAccountId());
       if (dbUser==null)
       {
           user.setGmtCreate(System.currentTimeMillis());
           user.setGmtModified(user.getGmtCreate());
           userMapper.insert(user);
           //插入
       }else {
           dbUser.setGmtModified(System.currentTimeMillis());
           dbUser.setAvatarurl(user.getAvatarurl());
           dbUser.setName(user.getName());
           dbUser.setToken(user.getToken());
           userMapper.update(dbUser);
           //更新
       }
    }
}
