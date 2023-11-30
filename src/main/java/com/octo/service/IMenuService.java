package com.octo.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.octo.entity.Menu;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author zms
 * @since 2023-11-23
 */
public interface IMenuService extends IService<Menu> {
    /**
     * 获取菜单列表
     *
     * @return
     */
    List<Tree<String>> getMenuList();

    /**
     * 获取菜单树选项
     *
     * @param menuName
     * @return
     */
    List<Tree<String>> getMenuTreeOptions(String menuName);

    /**
     * 保存菜单
     *
     * @param menu
     * @return
     */
    boolean saveMenu(Menu menu);
}
