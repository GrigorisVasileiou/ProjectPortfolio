package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

import java.awt.Desktop;
import java.net.URI;

@SpringBootApplication
public class Demo1Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo1Application.class, args);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> openAvastBrowserOnStart() {
        return event -> {
            new Thread(() -> {
                try {
                    String url = "http://localhost:8080/login";
                    String avastPath = "C:\\Program Files\\AVAST Software\\Browser\\Application\\AvastBrowser.exe";

                    Runtime.getRuntime().exec(new String[]{avastPath, url});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        };
    }
}