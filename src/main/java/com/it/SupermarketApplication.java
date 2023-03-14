package com.it;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.it.utils.DateUtils.stepMonth;

@SpringBootApplication
public class SupermarketApplication {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static void main(String[] args) {

        SpringApplication.run(SupermarketApplication.class, args);
        /*for (int i = 0; i < 34; i++) {
            System.out.println(RandomStringUtils.randomAlphanumeric(7));
        }*/



    }

}
