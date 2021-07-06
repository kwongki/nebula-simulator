package com.vantiq.simulatornebula;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;

@SpringBootApplication
//@Configuration
//@PropertySource(name = "myProperties", value = "app.properties")	// load from app.properties
public class NebulaWebsocketServer {
	//All the @Value annotations will try to load from properties file or System Environment

	//@Value("${input.file}")	
	//private String fileName;

	//@Value("${testProp}")
	//private String argValue;

	//@Autowired
	//private Environment env;

	public static void main(String[] args) {
		//System.out.println("command line args length = " + args.length);
		//System.setProperty("testProp", args[0]);   // Use this to set a commandline argument to environment property 
		ConfigurableApplicationContext context = SpringApplication.run(NebulaWebsocketServer.class, args);
		//context.getEnvironment().getPropertySources().addFirst(new SimpleCommandLinePropertySource(args)); // Add environment property from command line to Context
	}

	@PostConstruct
	public void afterInit() {
		//System.out.println("**************");
		//System.out.println(fileName);
		//System.out.println(argValue);
		//System.out.println(env.getProperty("test.prop2"));
		//System.out.println("**************");
	}
}
