package com.octo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.octo.dto.response.MenuResp;
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
    public List<MenuResp> getMenuList();

}
