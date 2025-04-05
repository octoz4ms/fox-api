package com.octo;

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
        String a = "";
        System.out.println(a == null);
    }

}
