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
 * 机构表
 * </p>
 *
 * @author zms
 * @since 2025-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_organization")
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 机构名称
     */
    private String organizationName;

    /**
     * 机构全称
     */
    private String organizationFullName;

    /**
     * 机构类型
     */
    private Integer organizationType;

    /**
     * 机构代码
     */
    private String organizationCode;

    /**
     * 上级机构
     */
    private Long parentId;

    /**
     * 排序
     */
    private Integer sortNumber;

    /**
     * 备注
     */
    private String comments;

    /**
     * 删除标识
     */
    private Integer deleted;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
