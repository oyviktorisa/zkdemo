package org.viktorisa.demoapp.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.viktorisa.demoapp.domain.DemoTask;
import org.viktorisa.demoapp.domain.DemoTaskExample;

public interface DemoTaskMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demo_task
     *
     * @mbggenerated Fri Mar 18 01:22:13 WIB 2016
     */
    int countByExample(DemoTaskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demo_task
     *
     * @mbggenerated Fri Mar 18 01:22:13 WIB 2016
     */
    int deleteByExample(DemoTaskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demo_task
     *
     * @mbggenerated Fri Mar 18 01:22:13 WIB 2016
     */
    int deleteByPrimaryKey(Integer taskId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demo_task
     *
     * @mbggenerated Fri Mar 18 01:22:13 WIB 2016
     */
    int insert(DemoTask record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demo_task
     *
     * @mbggenerated Fri Mar 18 01:22:13 WIB 2016
     */
    int insertSelective(DemoTask record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demo_task
     *
     * @mbggenerated Fri Mar 18 01:22:13 WIB 2016
     */
    List<DemoTask> selectByExample(DemoTaskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demo_task
     *
     * @mbggenerated Fri Mar 18 01:22:13 WIB 2016
     */
    DemoTask selectByPrimaryKey(Integer taskId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demo_task
     *
     * @mbggenerated Fri Mar 18 01:22:13 WIB 2016
     */
    int updateByExampleSelective(@Param("record") DemoTask record, @Param("example") DemoTaskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demo_task
     *
     * @mbggenerated Fri Mar 18 01:22:13 WIB 2016
     */
    int updateByExample(@Param("record") DemoTask record, @Param("example") DemoTaskExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demo_task
     *
     * @mbggenerated Fri Mar 18 01:22:13 WIB 2016
     */
    int updateByPrimaryKeySelective(DemoTask record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demo_task
     *
     * @mbggenerated Fri Mar 18 01:22:13 WIB 2016
     */
    int updateByPrimaryKey(DemoTask record);
}