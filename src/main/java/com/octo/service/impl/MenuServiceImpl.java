package com.octo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.octo.dto.response.MenuMetaResp;
import com.octo.entity.Menu;
import com.octo.mapper.MenuMapper;
import com.octo.service.IMenuService;
import com.octo.util.SnowIdUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author zms
 * @since 2023-11-23
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<Tree<String>> getMenuList() {
        List<Tree<String>> treeList = TreeUtil.build(list(), null, getConfig(), ((menu, treeNode) -> {
            treeNode.setId(menu.getMenuNo());
            treeNode.setParentId(menu.getParentNo());
            treeNode.setName(menu.getName());
            treeNode.setWeight(menu.getSort());
            treeNode.putExtra("path", menu.getPath());
            // meta信息
            MenuMetaResp meta = BeanUtil.toBean(menu, MenuMetaResp.class);
            meta.setOrder(menu.getSort());
            meta.setLocale(menu.getMenuName());
            treeNode.putExtra("meta", meta);
        }));
        return treeList;
    }

    @Override
    public List<Tree<String>> getMenuTreeOptions(String menuName) {
        List<Menu> menuList = list(Wrappers.lambdaQuery(Menu.class).like(Menu::getMenuName, menuName));
        List<Tree<String>> treeList = TreeUtil.build(menuList, null, getConfig(), ((menu, treeNode) -> {
            treeNode.setId(menu.getMenuNo());
            treeNode.setParentId(menu.getParentNo());
            treeNode.putExtra("menuName", menu.getMenuName());
        }));
        return treeList;
    }

    @Override
    public boolean saveMenu(Menu menu) {
        if (StrUtil.isEmpty(menu.getMenuNo())) {
            menu.setMenuNo(SnowIdUtil.nextIdStr());
        }
        return save(menu);
    }

    public TreeNodeConfig getConfig() {
        TreeNodeConfig config = new TreeNodeConfig();
        config.setIdKey("menuNo");
        config.setParentIdKey("parentNo");
        config.setWeightKey("sort");
        return config;
    }


}
