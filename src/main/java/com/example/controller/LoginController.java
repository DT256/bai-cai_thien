package com.example.controller;

import com.example.entity.Product;
import com.example.service.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private ProductServices service;

    @PostMapping("/login_success_handler")
    public String loginSuccessHandler() {
        System.out.println("Logging user login success...");

        return "index";
    }

    @PostMapping("/login_failure_handler")
    public String loginFailureHandler() {
        System.out.println("Login failure handler...");

        return "login";
    }

    @RequestMapping("/")
    public String viewHomePage(Model model) {
        List<Product> listProducts = service.listAll();
        model.addAttribute("listProducts", listProducts);
        return "index";
    }

    @RequestMapping("/new")
    public String showNewProductForm(Model model, @ModelAttribute("product") Product product ) {
        model.addAttribute("product",product);
        return "new_product";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("product") Product product) {
        service.save(product);
        return "redirect:/";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditProductForm(@PathVariable("id") Long id, Model model) {
        ModelAndView mav = new ModelAndView("edit_product");

        Product product = service.get(id);
        mav.addObject("product",product);
        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        service.delete(id);
        return "redirect:/";
    }
}
