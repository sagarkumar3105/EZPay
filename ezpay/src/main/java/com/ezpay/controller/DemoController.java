package com.ezpay.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

	@GetMapping("/test")
	public String testController() {
		return "Welcome from Test Controller";
	}
}
