package com.rebox.mapper;

import com.rebox.domain.po.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    User selectById(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User login(String loginAct);

    int selectByCount();

    List<User> selectByPage(int start, int pageSize);

    int deleteByIds(List<String> idList);

    List<User> selectByAll();

    Integer countByAct(String loginAct);
}