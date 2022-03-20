package com.kk.api;

import com.kk.api.core.constant.ProjectConstant;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import tk.mybatis.spring.annotation.MapperScan;

import javax.annotation.PostConstruct;
import java.util.TimeZone;


/**
 * 主程序
 *
 * @author 0
 * @date 2018/05/27
 */
@EnableCaching
@SpringBootApplication
@EnableEncryptableProperties
@EnableTransactionManagement
@MapperScan(basePackages = ProjectConstant.MAPPER_PACKAGE)
@ServletComponentScan(basePackages = ProjectConstant.FILTER_PACKAGE)
public class Application extends SpringBootServletInitializer {

  @PostConstruct
  void started() {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
  }

  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }

  /** 容器启动配置 */
  @Override
  protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
    return builder.sources(Application.class);
  }


}
