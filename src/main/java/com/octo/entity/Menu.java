package com.octo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author zms
 * @since 2025-04-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父级菜单
     */
    private Long parentId;

    /**
     * 权限标识
     */
    private String authority;

    /**
     * 菜单名称
     */
    private String title;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件地址
     */
    private String component;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序
     */
    private Long sortNumber;

    /**
     * 组件元数据
     */
    private String meta;

    /**
     * 是否展示：0展示，1隐藏
     */
    private Boolean hide;

    /**
     * 菜单类型：0目录，1菜单，2按钮
     */
    private Integer menuType;

    /**
     * 打开方式：0组件，1内嵌，2外链
     */
    private Integer openType;

    /**
     * 删除标识：0未删除，1已删除
     */
    @TableLogic
    private Boolean deleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
