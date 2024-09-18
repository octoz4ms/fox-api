package com.octo;

import com.octo.util.FileUploadUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ApplicationTests {

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Test
    void contextLoads() {
        String baseUploadDirectory = FileUploadUtil.baseUploadDirectory;
        System.out.println(baseUploadDirectory);


    }

}
