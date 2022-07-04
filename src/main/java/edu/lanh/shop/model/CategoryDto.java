package edu.lanh.shop.model;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto{
	private Long categoryid;
	@NotEmpty
	@Length(min = 5)
	private String categoryname;
	
	private Boolean isEdit = false;
	
}
