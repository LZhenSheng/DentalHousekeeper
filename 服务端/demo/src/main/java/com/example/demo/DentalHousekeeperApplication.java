package com.example.demo;

import lombok.extern.apachecommons.CommonsLog;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.example.demo.mapper")
@ComponentScan({"com.example.demo"})
public class DentalHousekeeperApplication {

	public static void main(String[] args) {
		SpringApplication.run(DentalHousekeeperApplication.class, args);
	}

}
