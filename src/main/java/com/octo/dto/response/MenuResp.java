package com.octo.dto.response;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.octo.entity.Menu;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zms
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MenuResp {
    private String path;

    private String name;

    private MenuMetaResp meta;

    private List<MenuResp> children;

    /**
     * 递归构建菜单树
     *
     * @param menuList
     * @return
     */
    public static List<MenuResp> buildMenuTree(List<Menu> menuList) {
        List<MenuResp> tree = new ArrayList<>();
        for (Menu menu : menuList) {
            if (menu.getParentNo() == null) {
                MenuResp node = buildTreeNode(menuList, menu);
                tree.add(node);
            }
        }
        return tree;
    }

    /**
     * 递归构建子菜单
     *
     * @param parentNo
     * @param menuList
     * @return
     */
    private static List<MenuResp> buildChildren(String parentNo, List<Menu> menuList) {
        List<MenuResp> children = new ArrayList<>();
        for (Menu menu : menuList) {
            if (parentNo.equals(menu.getParentNo())) {
                MenuResp node = buildTreeNode(menuList, menu);
                children.add(node);
            }
        }
        return children;
    }

    /**
     * 构建菜单节点
     *
     * @param menuList
     * @param menu
     */
    private static MenuResp buildTreeNode(List<Menu> menuList, Menu menu) {
        MenuResp node = new MenuResp();
        node.setName(menu.getName());
        node.setPath(menu.getPath());
        node.setMeta(BeanUtil.toBean(menu, MenuMetaResp.class));
        // 排序字段名称不一致（单独处理）
        node.getMeta().setOrder(menu.getSort());
        node.setChildren(buildChildren(menu.getMenuNo(), menuList));
        return node;
    }
}
