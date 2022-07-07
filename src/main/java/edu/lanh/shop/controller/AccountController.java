package edu.lanh.shop.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.lanh.shop.domain.Account;
import edu.lanh.shop.model.AccountDto;
import edu.lanh.shop.model.ProductDto;
import edu.lanh.shop.service.AccountService;

@Controller
@RequestMapping("/admin/accounts")
public class AccountController {
	@Autowired
	private AccountService accountService;
	
	@GetMapping("add")
	public String add(Model model) {
		AccountDto accountDto = new AccountDto();
		accountDto.setIsEdit(false);
		model.addAttribute("accountDto", new AccountDto());
		return "admin/account/addOrEdit";
	}
	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, 
			@Valid @ModelAttribute("accountDto") AccountDto accountDto, BindingResult result) {
		if(result.hasErrors()) {
			return new ModelAndView("admin/category/addOrEdit");
		}
		Account account = new Account();
		BeanUtils.copyProperties(accountDto, account);
		accountService.save(account);
		model.addAttribute("message", "Account is saved");
		return new ModelAndView("forward:/admin/accounts", model);
	}
	
	
//	@GetMapping("edit/{categoryId}")
//	public ModelAndView edit(ModelMap model, @PathVariable("categoryId") Long categoryId) {
//		Optional<Category> opt = categoryService.findById(categoryId);
//		CategoryDto categoryDto = new CategoryDto();
//		if(opt.isPresent()) {
//			Category entity = opt.get();
//			BeanUtils.copyProperties(entity, categoryDto);
//			categoryDto.setIsEdit(true);
//			model.addAttribute("categoryDto", categoryDto);
//			return new ModelAndView("admin/category/addOrEdit", model);
//		}
//		model.addAttribute("message", "Category is not existed");
//		return new ModelAndView("redirect:/admin/categories", model);
//	}
//	
//	@GetMapping("delete/{categoryId}")
//	public ModelAndView delete(ModelMap model, @PathVariable("categoryId") Long categoryId) {
//		categoryService.deleteById(categoryId);
//		model.addAttribute("message", "Category is deleted!");
//		return new ModelAndView("forward:/admin/categories", model);
//	}
//	

//	
//	@RequestMapping("")
//	public String list(ModelMap model) {
//		List<Category> list = categoryService.findAll();
//		model.addAttribute("categories", list);
//		return "admin/category/list";
//	}
//	
//	@GetMapping("search")
//	public String search(ModelMap model, @RequestParam(name = "name", required=false) String name) {
//		List<Category> list = null;
//		if(StringUtils.hasText(name)) {
//			list = categoryService.findByCategorynameContaining(name);
//		} else list = categoryService.findAll();
//		model.addAttribute("categories", list);
//		return "admin/category/search";
//	}
//	
//	@GetMapping("search/pagingated")
//	public String search(ModelMap model, @RequestParam(name = "name", required=false) String name,
//				@RequestParam("page") Optional<Integer> page,
//				@RequestParam("size") Optional<Integer> size) {
//		int currentPage = page.orElse(1);
//		int pageSize = size.orElse(5);
//		
//		Pageable pageable = PageRequest.of(currentPage-1, pageSize, Sort.by("categoryname"));
//		Page<Category> resultPage = null;
//		if(StringUtils.hasText(name)) {
//			resultPage = categoryService.findByCategorynameContaining(name, pageable);
//			model.addAttribute("name", name);
//		} else resultPage = categoryService.findAll(pageable);
//		
//		int totalPages = resultPage.getTotalPages();
//		if (totalPages > 0) {
//			int start = Math.max(1, currentPage - 2);
//			int end = Math.min(currentPage + 2, totalPages);
//			if (totalPages > 5) {
//				if (end == totalPages)
//					start = end - 5;
//				else if (start == 1)
//					end = start + 5;
//			}
//			List<Integer> pages = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
//			model.addAttribute("pages", pages);
//		}
//		
//		model.addAttribute("categoriesPage", resultPage);
//		return "admin/category/searchpaginated";
//	}
}
