package com.cas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;

@RestController
@SpringBootApplication
public class HomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeApplication.class, args);
	}

	@RequestMapping(value = "/index",method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}

	@RequestMapping("/hello")
	public String hello() {
		return "不验证哦";
	}

	@PreAuthorize("hasAuthority('TEST')")//有TEST权限的才能访问
	@RequestMapping("/security")
	public String security() {
		return "hello world security";
	}

	@PreAuthorize("hasAuthority('ADMIN')")//必须要有ADMIN权限的才能访问
	@RequestMapping("/authorize")
	public String authorize() {
		return "有权限访问";
	}
}
