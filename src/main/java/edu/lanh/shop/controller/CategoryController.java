package edu.lanh.shop.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.lanh.shop.domain.Category;
import edu.lanh.shop.model.CategoryDto;
import edu.lanh.shop.service.CategoryService;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("categoryDto", new CategoryDto());
		return "admin/category/addOrEdit";
	}
	
	
	@GetMapping("edit/{categoryId}")
	public String edit() {
		return "admin/category/addOrEdit";
	}
	
	@GetMapping("delete/{categoryId}")
	public String delete() {
		return "redirect:/admin/categories/list";
	}
	
	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, CategoryDto categoryDto) {
		Category category = new Category();
		BeanUtils.copyProperties(categoryDto, category);
		categoryService.save(category);
		model.addAttribute("message", "Categor is saved");
		return new ModelAndView("redirect:/admin/categories", model);
	}
	
	@GetMapping("")
	public String list(ModelMap model) {
		List<Category> list = categoryService.findAll();
		model.addAttribute("categories", list);
		return "admin/category/list";
	}
	
	@GetMapping("search")
	public String search() {
		return "admin/category/search";
	}
}
