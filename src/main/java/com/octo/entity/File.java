package com.octo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文件表
 * </p>
 *
 * @author zms
 * @since 2025-04-02
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
    private String name;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 访问路径
     */
    private String url;

    /**
     * 文件大小
     */
    private Long length;

    /**
     * 上传用户NO
     */
    private String creator;

    /**
     * 上传用户名称
     */
    private String creatorName;

    /**
     * 上传时间
     */
    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
