package edu.lanh.shop.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	public ModelAndView edit(ModelMap model, @PathVariable("categoryId") Long categoryId) {
		Optional<Category> opt = categoryService.findById(categoryId);
		CategoryDto categoryDto = new CategoryDto();
		if(opt.isPresent()) {
			Category entity = opt.get();
			BeanUtils.copyProperties(entity, categoryDto);
			categoryDto.setIsEdit(true);
			model.addAttribute("categoryDto", categoryDto);
			return new ModelAndView("admin/category/addOrEdit", model);
		}
		model.addAttribute("message", "Category is not existed");
		return new ModelAndView("redirect:/admin/categories", model);
	}
	
	@GetMapping("delete/{categoryId}")
	public String delete() {
		return "redirect:/admin/categories/list";
	}
	
	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, 
			@Valid @ModelAttribute("categoryDto") CategoryDto categoryDto, BindingResult result) {
		if(result.hasErrors()) {
			return new ModelAndView("admin/category/addOrEdit");
		}
		Category category = new Category();
		BeanUtils.copyProperties(categoryDto, category);
		categoryService.save(category);
		model.addAttribute("message", "Category is saved");
		return new ModelAndView("forward:/admin/categories", model);
	}
	
	@RequestMapping("")
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
