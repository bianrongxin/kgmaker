package com.warmer.kgmaker;

import com.warmer.kgmaker.controller.SGNodeController;
import com.warmer.kgmaker.entity.SGNode;
import com.warmer.kgmaker.repository.SGNodeReponsitory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Set;

@SpringBootApplication
@ComponentScan
public class KgmakerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KgmakerApplication.class, args);
	}
}
