package com.example.reggie;

import org.junit.jupiter.api.Test;

public class UploadFileTests {
    @Test
    public void test1() {
        String t = "tests.jpg";
        String substring = t.substring(t.lastIndexOf("."));
        System.out.println(substring);
    }
}
