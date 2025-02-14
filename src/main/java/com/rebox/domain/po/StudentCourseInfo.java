package com.rebox.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName StudentCourseInfo
 * @Descrition 学生课程表
 * @Author
 * @Date 2024-06-21
 * @Version V1.0.0
 **/
@Data
@TableName("student_course_info")
@ApiModel(value = "StudentCourseInfo对象", description = "学生课程表")
public class StudentCourseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学生id
     */
    @ApiModelProperty(value = "学生id")
    private Integer studentId;

    @TableField(exist = false)
    private String studentName;

    /**
     * 课程id
     */
    @ApiModelProperty(value = "课程id")
    private Integer courseId;

    @TableField(exist = false)
    private String courseName;

    /**
     * 年级
     */
    @ApiModelProperty(value = "年级")
    private String grade;

    /**
     * 学期
     */
    @ApiModelProperty(value = "学期")
    private String term;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
