package edu.lanh.shop.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="categories")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoryid;
	
	@Column(name="category_name", length=100,
			columnDefinition="nvarchar(100) not null")
	private String categoryname;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private Set<Product> products;
	
	
}
