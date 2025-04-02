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
 * 菜单表
 * </p>
 *
 * @author zms
 * @since 2025-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String parentId;

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
     * 是否展示
     */
    private Boolean hide;

    /**
     * 菜单类型：0目录/菜单，1按钮
     */
    private Integer menuType;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
