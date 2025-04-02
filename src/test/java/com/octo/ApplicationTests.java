package com.octo;

import com.octo.entity.User;
import com.octo.service.impl.UserServiceImpl;
import com.octo.util.FileUploadUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ApplicationTests {

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    void contextLoads() {
        User user = new User().setId(2L);
        boolean save = userServiceImpl.save(user);
        System.out.println(save);
    }

}
