package com.octo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文件管理
 * </p>
 *
 * @author zms
 * @since 2024-09-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_file")
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文件名
     */
    @TableField("name")
    private String name;

    /**
     * 文件路径
     */
    @TableField("path")
    private String path;

    /**
     * 访问路径
     */
    @TableField("url")
    private String url;

    /**
     * 文件大小
     */
    @TableField("length")
    private Long length;

    /**
     * 上传用户NO
     */
    @TableField("creator")
    private String creator;

    /**
     * 上传用户名称
     */
    @TableField("creator_name")
    private String creatorName;

    /**
     * 上传时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;


}
