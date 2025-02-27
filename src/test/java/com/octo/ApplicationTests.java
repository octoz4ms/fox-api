package com.octo;

import com.octo.util.FileUploadUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.nio.file.Paths;


@SpringBootTest
class ApplicationTests {

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Test
    void contextLoads() {
        Path path = Paths.get("upload", "images");
        System.out.println(path);
        Path resolve = path.resolve("hello.txt");
        System.out.println(resolve);
        System.out.println(resolve.toString());
    }

}
