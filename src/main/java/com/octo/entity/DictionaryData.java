package com.octo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 字典数据表
 * </p>
 *
 * @author zms
 * @since 2025-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_dictionary_data")
public class DictionaryData implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 字典数据名
     */
    private String dictDataName;

    /**
     * 字典数据值
     */
    private String dictDataCode;

    /**
     * 排序
     */
    private Integer sortNumber;

    /**
     * 备注
     */
    private String comments;

    /**
     * 字典ID
     */
    private Long dictId;

    /**
     * 删除标识：0未删除，1已删除
     */
    @TableLogic
    private Integer deleted;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
