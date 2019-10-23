package com.royal;

import com.royal.commen.socket.WebsocketClient;
import com.royal.task.ThreadPoolUtil;
import com.royal.util.OpenCloseMaxMinUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//import com.royal.commen.Logger;

import tk.mybatis.spring.annotation.MapperScan;

import javax.servlet.MultipartConfigElement;

@Controller
@SpringBootApplication
@Configuration
@EnableTransactionManagement//开启注解事务管理
@MapperScan("com.royal.mapper")
public class RoyalApplication  {
//	protected static Logger logger = Logger.getLogger(RoyalApplication.class);

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(RoyalApplication.class);
//	}

	public static void main(String[] args) {
//		logger.info("启动了");
		SpringApplication.run(RoyalApplication.class, args);
		ThreadPoolUtil.entryOrders();
		OpenCloseMaxMinUtils.initMaxAndMin();
		if(WebsocketClient.client==null||WebsocketClient.client.isClosed ()){
			WebsocketClient.open ();
		}



		// SpringApplication application = new SpringApplication(
		// TestApplication.class);
		// application.setBannerMode(Mode.OFF);
		// application.run(args);
	}

	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setLocation("/mnt/jar/tmp/royal");
		return factory.createMultipartConfig();
	}
}
