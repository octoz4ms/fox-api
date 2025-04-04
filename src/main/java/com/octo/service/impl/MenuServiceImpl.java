package com.octo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.octo.entity.Menu;
import com.octo.mapper.MenuMapper;
import com.octo.service.IMenuService;
import com.octo.service.IRoleMenuService;
import com.octo.util.ApiResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private IRoleMenuService roleMenuService;

    @Override
    public boolean saveMenu(Menu menu) {
        return false;
    }

    @Override
    public List<Menu> getMenuList(String title, String path, String authority) {
        return List.of();
    }

    @Override
    public ApiResponse deleteMenu(String menuNo) {
        return null;
    }

//    @Override
//    public boolean saveMenu(Menu menu) {
//        if (StrUtil.isEmpty(menu.getMenuNo())) {
//            menu.setMenuNo(GeneralUtil.nextIdStr());
//            return save(menu);
//        }
//        return update(menu, new LambdaUpdateWrapper<Menu>().eq(Menu::getMenuNo, menu.getMenuNo()));
//    }
//
//    @Override
//    public List<Menu> getMenuList(String title, String path, String authority) {
//        LambdaQueryWrapper<Menu> queryWrapper = Wrappers.lambdaQuery(Menu.class)
//                .like(StrUtil.isNotBlank(title), Menu::getTitle, title)
//                .like(StrUtil.isNotBlank(path), Menu::getPath, path)
//                .like(StrUtil.isNotBlank(authority), Menu::getAuthority, authority);
//        return list(queryWrapper);
//    }
//
//    @Override
//    public ApiResponse deleteMenu(String menuNo) {
//        if (StrUtil.isBlank(menuNo)) {
//            return ApiResponse.fail();
//        }
//        List<RoleMenu> list = roleMenuService.list(Wrappers.lambdaQuery(RoleMenu.class).eq(RoleMenu::getMenuNo, menuNo));
//        if (list.size() > 0) {
//            return ApiResponse.fail("请先与角色解绑");
//        }
//        boolean result = remove(new LambdaQueryWrapper<Menu>().eq(Menu::getMenuNo, menuNo));
//        return result ? ApiResponse.success() : ApiResponse.fail();
//    }
}
