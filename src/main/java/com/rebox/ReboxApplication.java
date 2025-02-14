package com.rebox;

import com.rebox.commons.BaseApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

//@MapperScan(basePackages = {"com.rebox.mapper"})

@MapperScan("com.rebox.mapper")
//@EnablePrometheusEndpoint
//@EnableSpringBootMetricsCollector
@SpringBootApplication
//@EnableScheduling
public class ReboxApplication extends BaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReboxApplication.class, args);
	}

}