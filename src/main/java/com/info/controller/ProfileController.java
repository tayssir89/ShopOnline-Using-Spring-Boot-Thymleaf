package com.info.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.info.model.Product;
import com.info.model.User;
import com.info.service.ProductService;
import com.info.service.UserService;

@Controller
@RequestMapping("profile")
public class ProfileController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("cart-product")
	public ModelAndView cartProduct(Principal principal) {
		ModelAndView mv = new ModelAndView("profile/cart-product");
		User user = userService.findByEmail(principal.getName());
		mv.addObject("user", user);
		int total = findSum(user);
		mv.addObject("total", total);
		return mv;
	}
	
	private int findSum(User user) {
		List<Product> list = user.getProductList();
		int sum =0;
		for(int i=0; i<list.size(); i++) {
			Product p = list.get(i);
			sum+= p.getProductPrice();
		}
		return sum;
	}

	@GetMapping("addToCart/{productId}")
	public ModelAndView addToCart(@PathVariable("productId")String productId,Principal principal) {
		ModelAndView mv = new ModelAndView("profile/cart-product");
		User user = userService.findByEmail(principal.getName());
		long productLongId = Long.parseLong(productId);
		Product product = productService.getProductById(productLongId).get();
		
		List<Product> productList = new ArrayList<Product>();
		productList.add(product);
		user.setProductList(productList);
		
		List<User> userList = new ArrayList<>();
		userList.add(user);
		product.setUserList(userList);
		
		userService.update(user);
		productService.addProduct(product);
		int total = findSum(user);
		mv.addObject("total", total);
		mv.addObject("user", user);
		return mv;
	}
	
}
