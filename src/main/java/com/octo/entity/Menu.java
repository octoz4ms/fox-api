package com.octo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author zms
 * @since 2024-01-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String menuNo;

    /**
     * 上级菜单NO
     */
    private String parentNo;

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
     * 是否展示
     */
    private Boolean hide;

    /**
     * 菜单类型：0目录或菜单、1按钮
     */
    private Integer menuType;

    /**
     * 打开方式：0组件、1内链、2外链
     */
    private Integer openType;

    /**
     * 权限标识
     */
    private String authority;


    @TableField(exist = false)
    private boolean checked;


}
