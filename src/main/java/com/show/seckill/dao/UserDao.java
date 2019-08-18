package com.show.seckill.dao;

import com.show.seckill.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @Auther: 涂成
 * @Date: 2019/6/18 10:36
 * @Description:
 */
@Mapper
//@Component
public interface UserDao {

    @Select("SELECT * FROM user WHERE user_id = #{userId}")
    public User queryUserById(@Param("userId") int userId);

    @Insert("INSERT INTO user VALUES (#{userId},#{userName})")
    public int addUser(User user);
}
