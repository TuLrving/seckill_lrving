package com.show.seckill.dao;

import com.show.seckill.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @Auther: 涂成
 * @Date: 2019/6/19 09:42
 * @Description:
 */
@Mapper
public interface MiaoshaUserDao {

    @Select("SELECT * FROM miaosha_user WHERE id = #{id}")
    MiaoshaUser getUserById(@Param("id") Long id);

    @Update("UPDATE miaosha_user SET password=#{password} WHERE id=#{id}")
    int updatePass(MiaoshaUser updatedUser);
}
