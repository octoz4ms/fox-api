package com.octo.service;

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

    List<Menu> listMenus(String title, String path, String authority);

    void updateMenu(Menu menu);

    void deleteMenu(Long id);
}
