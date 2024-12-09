package com.example.onlineshopping.mapping;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.onlineshopping.entity.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    //查询全部
    @DS("slave")
    @Select("select * from user")
    List<UserInfo> findAll();

    //新增数据
    @DS("slave")
    @Insert("insert into user (username,password) values (#{username},#{password})")
    public int save(UserInfo userInfo);

    //删除数据
    @DS("slave")
    @Delete("delete from user where id=#{id}")
    public int delete(int id);

    //根据id查找
    @DS("slave")
    @Select("select * from user where id=#{id}")
    public UserInfo get(int id);

    //更新数据
    @DS("slave")
    @Update("update user set username=#{username},password=#{password} where id=#{id}")
    public int update(UserInfo userInfo);

}