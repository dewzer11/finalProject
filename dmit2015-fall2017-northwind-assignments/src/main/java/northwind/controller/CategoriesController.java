package northwind.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import northwind.data.CategoriesRepository;
import northwind.model.Category;
import northwind.report.CategorySales1997;

@Model
public class CategoriesController {

	@Inject
	private CategoriesRepository CategoriesRepository;
	
	private List<Category> Categories;
	
	@PostConstruct
	void init() {
		Categories = CategoriesRepository.findAll();
	}

	public List<Category> getCategories() {
		return Categories;
	}
	
	public List<CategorySales1997> retrieveCategorySales() {
		return CategoriesRepository.findCategorySales();
	}
}
