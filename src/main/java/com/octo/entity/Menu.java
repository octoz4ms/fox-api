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
 * 菜单表
 * </p>
 *
 * @author zms
 * @since 2024-07-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("menu_no")
    private String menuNo;

    /**
     * 上级菜单NO
     */
    @TableField("parent_no")
    private String parentNo;

    /**
     * 权限标识
     */
    @TableField("authority")
    private String authority;

    /**
     * 菜单名称
     */
    @TableField("title")
    private String title;

    /**
     * 路由地址
     */
    @TableField("path")
    private String path;

    /**
     * 组件地址
     */
    @TableField("component")
    private String component;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 排序
     */
    @TableField("sort_number")
    private Long sortNumber;

    /**
     * 是否展示
     */
    @TableField("hide")
    private Boolean hide;

    @TableField("checked")
    private Boolean checked;

    /**
     * 菜单类型：0目录或菜单、1按钮
     */
    @TableField("menu_type")
    private Integer menuType;

    /**
     * 打开方式：0组件、1内链、2外链
     */
    @TableField("open_type")
    private Integer openType;

    @TableField("createTime")
    private LocalDateTime createTime;

    @TableField("updateTime")
    private LocalDateTime updateTime;


}
