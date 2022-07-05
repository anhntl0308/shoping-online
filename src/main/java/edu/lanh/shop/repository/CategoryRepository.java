package edu.lanh.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.lanh.shop.domain.Category;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Long>{
	List<Category> findByCategorynameContaining(String categoryname);
}
