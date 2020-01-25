package lin.community.communtiy.mapper;

import lin.community.communtiy.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    @Insert("insert into user(id,account_id,name,token,gmt_Create,gmt_Modified,avatar_url) values (#{id},#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarurl})")
    void insert(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Integer id);
}
