package com.example.Test;

import org.junit.Test;

public class test1 {
    @Test
    public void test1(){
        String name="123456789!";
        String[] s = name.split(" ");
        System.out.println(s.length);
        System.out.println(name.substring(2, name.length()));

    }
}
