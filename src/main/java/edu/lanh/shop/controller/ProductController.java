package edu.lanh.shop.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.lanh.shop.domain.Category;
import edu.lanh.shop.domain.Product;
import edu.lanh.shop.model.CategoryDto;
import edu.lanh.shop.model.ProductDto;
import edu.lanh.shop.service.CategoryService;
import edu.lanh.shop.service.ProductService;
import edu.lanh.shop.service.StorageService;

@Controller
@RequestMapping("/admin/products")
public class ProductController {
	@Autowired
	private CategoryService categoryService;
	
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	StorageService storageService;
	
	@ModelAttribute("categories")
	public List<CategoryDto> getCategories(){
		return categoryService.findAll().stream().map(item -> {
			CategoryDto categoryDto = new CategoryDto();
			BeanUtils.copyProperties(item, categoryDto);
			return categoryDto;
		}).toList();
	}
	@GetMapping("add")
	public String add(Model model) {
		ProductDto productDto = new ProductDto();
		productDto.setIsEdit(false);

		model.addAttribute("productDto", new ProductDto());
		return "admin/product/addOrEdit";
	}
	
	@GetMapping("edit/{productId}")
	public ModelAndView edit(ModelMap model, @PathVariable("productId") Long productId) {
		Optional<Product> opt = productService.findById(productId);
		ProductDto productDto = new ProductDto();
		if(opt.isPresent()) {
			Product entity = opt.get();
			BeanUtils.copyProperties(entity, productDto);
			productDto.setCategoryId(entity.getCategory().getCategoryid());
			productDto.setIsEdit(true);
			model.addAttribute("productDto", productDto);
			return new ModelAndView("admin/product/addOrEdit", model);
		}
		model.addAttribute("message", "Product is not existed");
		return new ModelAndView("redirect:/admin/products", model);
	}
	
	@GetMapping("/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename){
		Resource file = storageService.loadAsResourcce(filename);
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, 
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
	
	
	@GetMapping("delete/{productId}")
	public ModelAndView delete(ModelMap model, @PathVariable("productId") Long productId) 
			throws IOException {
		Optional<Product> opt = productService.findById(productId);
		if(opt.isPresent()) {
			if(!StringUtils.isEmpty("uploads/images/"+opt.get().getImage())) {
				storageService.delete("uploads/images/"+opt.get().getImage());
			}
			productService.delete(opt.get());
			model.addAttribute("message", "Product is deleted!");
		} else {
			model.addAttribute("message", "Product is not found!");
		}
		return new ModelAndView("forward:/admin/products", model);
	}
	
	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, 
			@Valid @ModelAttribute("productDto") ProductDto productDto, BindingResult result) {
		if(result.hasErrors()) {
			return new ModelAndView("admin/product/addOrEdit");
		}
		Product product = new Product();
		BeanUtils.copyProperties(productDto, product);
		
		Category category = new Category();
		category.setCategoryid(productDto.getCategoryId());
		product.setCategory(category);
		
		if(!productDto.getImageFile().isEmpty()) {
			UUID uuid = UUID.randomUUID();
			String uuString = uuid.toString();
			product.setImage(storageService.getStoredFileName(productDto.getImageFile(), uuString));
			storageService.store(productDto.getImageFile(), product.getImage());
		}
		productService.save(product);
		model.addAttribute("message", "Product is saved");
		return new ModelAndView("forward:/admin/products", model);
	}
	
	@RequestMapping("")
	public String list(ModelMap model) {
		List<Product> list = productService.findAll();
		model.addAttribute("products", list);
		return "admin/product/list";
	}
	
	@GetMapping("search")
	public String search(ModelMap model, @RequestParam(name = "name", required=false) String name) {
		List<Category> list = null;
		if(StringUtils.hasText(name)) {
			list = categoryService.findByCategorynameContaining(name);
		} else list = categoryService.findAll();
		model.addAttribute("categories", list);
		return "admin/category/search";
	}
	
	@GetMapping("search/pagingated")
	public String search(ModelMap model, @RequestParam(name = "name", required=false) String name,
				@RequestParam("page") Optional<Integer> page,
				@RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);
		
		Pageable pageable = PageRequest.of(currentPage-1, pageSize, Sort.by("categoryname"));
		Page<Category> resultPage = null;
		if(StringUtils.hasText(name)) {
			resultPage = categoryService.findByCategorynameContaining(name, pageable);
			model.addAttribute("name", name);
		} else resultPage = categoryService.findAll(pageable);
		
		int totalPages = resultPage.getTotalPages();
		if (totalPages > 0) {
			int start = Math.max(1, currentPage - 2);
			int end = Math.min(currentPage + 2, totalPages);
			if (totalPages > 5) {
				if (end == totalPages)
					start = end - 5;
				else if (start == 1)
					end = start + 5;
			}
			List<Integer> pages = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}
		
		model.addAttribute("categoriesPage", resultPage);
		return "admin/category/searchpaginated";
	}
}
