package com.info.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.info.model.User;
import com.info.service.CategoryService;
import com.info.service.ProductService;
import com.info.service.UserService;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping({"index", "/"})
	public String index(Model model) {
		model.addAttribute("categoryList", categoryService.listCategory());
		model.addAttribute("productList", productService.listProduct());
		return "index";
	}
	
	@GetMapping("login")
	public String login() {
		return "login";
	}
	
	@GetMapping("signup")
	public String signup() {
		return "signup";
	}
	
	@PostMapping("signup")
	public ModelAndView signUp(User user) {
		ModelAndView mv = new ModelAndView("/index");
		userService.save(user);
		mv.addObject("productList", productService.listProduct());
		mv.addObject("categoryList", categoryService.listCategory());
		return mv;
	}
	
	@GetMapping("allProduct")
	public String allProduct(Model model) {
		model.addAttribute("productList", productService.listProduct());
		model.addAttribute("categoryList", categoryService.listCategory());
		return "index";
	}
	
	@GetMapping("getProducts/{categoryId}")
	public ModelAndView getProductFromCategory(@PathVariable("categoryId")String categoryId) {
		ModelAndView mv = new ModelAndView("index");
		long categoryLongId = Long.parseLong(categoryId);
		System.out.println(categoryLongId);
		mv.addObject("productList", productService.findByCategory(categoryLongId));
		mv.addObject("categoryList", categoryService.listCategory());
		return mv;
	}
	
	@GetMapping("error")
	public String error() {
		return "error";
	}
	
}
