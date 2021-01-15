package com.offcn.user.mapper;

import com.offcn.user.po.TCert;
import com.offcn.user.po.TCertExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TCertMapper {
    long countByExample(TCertExample example);

    int deleteByExample(TCertExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TCert record);

    int insertSelective(TCert record);

    List<TCert> selectByExample(TCertExample example);

    TCert selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TCert record, @Param("example") TCertExample example);

    int updateByExample(@Param("record") TCert record, @Param("example") TCertExample example);

    int updateByPrimaryKeySelective(TCert record);

    int updateByPrimaryKey(TCert record);
}