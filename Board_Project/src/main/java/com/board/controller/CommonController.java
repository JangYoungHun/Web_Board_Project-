package com.board.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;


@Log4j
@Controller
@RequestMapping("/sample/")
public class CommonController {
	
	@RequestMapping("accessError")
	public void accessError(Authentication auth, Model model){
		model.addAttribute("msg", "Access Denied");
	}
	
	@GetMapping("customLogin")
	public void loginInfo(String error, String logout, Model model) {
		if(error != null) {
			model.addAttribute("error", "Login Error Check Your Account");
		}
		if(logout != null) {
			model.addAttribute("logout", "Logout!!");
		}
	}
	
	@GetMapping("customLogout")
	public void customLogout(){
		log.info("Custom Logout");
	}
}
