package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.*;

@SpringBootTest
class Demo1ApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("Το Spring Boot ξεκίνησε σωστά!");
    }
}