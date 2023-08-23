package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/signup")
public class SignUpController {

	@Autowired
	private UserService userService;

	@GetMapping
	public String initSignUp() {
		return "signup";
	}

	@PostMapping
	public String createUser(User user, Model model, RedirectAttributes redirectAttributes) {
		boolean isExist = false;
		int result = 0;
		if(userService.getUser(user.getUsername()) != null) {
			isExist = true;
		} else {
			result = userService.createUser(user);
		}
		
		if (isExist) {
			model.addAttribute("messageError", "This account already exists!");
			return "signup";
		}

		if (result <= 0) {
			model.addAttribute("messageError", "Sign Up error. Please try again later!");
			return "signup";
		}else {
			redirectAttributes.addFlashAttribute("success", "You successfully signed up. Please continue to Login!");
			return "redirect:/login";
		}
	}

}
