package edu.lanh.shop.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.codehaus.groovy.util.StringUtil;
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
import org.thymeleaf.util.StringUtils;

import edu.lanh.shop.domain.Account;
import edu.lanh.shop.model.AccountDto;
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
	@RequestMapping("")
	public String list(ModelMap model) {
		List<Account> list = accountService.findAll();
		model.addAttribute("accounts", list);
		return "admin/account/list";
	}
	
	@GetMapping("delete/{username}")
	public ModelAndView delete(ModelMap model, @PathVariable("username") String username) {
		Account account = accountService.getOne(username);
		if(account!=null) {
			accountService.delete(account);
			model.addAttribute("message", "Account is deleted!");
		} else {

			model.addAttribute("message", "Account is not exist!");
		}
		return new ModelAndView("forward:/admin/accounts", model);
	}
	@GetMapping("edit/{username}")
	public ModelAndView edit(ModelMap model, @PathVariable("username") String username) {
		Optional<Account> opt = accountService.findById(username);
		AccountDto accountDto = new AccountDto();
		if(opt.isPresent()) {
			Account entity = opt.get();
			BeanUtils.copyProperties(entity, accountDto);
			accountDto.setIsEdit(true);
			accountDto.setPassword("");
			model.addAttribute("accountDto", accountDto);
			return new ModelAndView("admin/account/addOrEdit", model);
		}
		model.addAttribute("message", "Account is not existed");
		return new ModelAndView("redirect:/admin/accounts", model);
	}
	
	

//	

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
