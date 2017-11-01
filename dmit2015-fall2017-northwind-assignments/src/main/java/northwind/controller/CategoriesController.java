package northwind.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.omnifaces.util.Faces;

import net.bootsfaces.component.messages.Messages;
import northwind.data.CategoriesRepository;
import northwind.model.Category;
import northwind.report.CategorySales1997;
import northwind.service.CategoryService;

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
	
	private CategoryService categoryService;
	
	@NotBlank(message="Categor Name value is required")
	@Length
	private String categoryName; // +getter+setter
	
	public void createNewCategory() {
		try	{
			categoryService.createCategory(categoryName);
			Messages.addGlobalInfo("Create Category was successful");
			categoryName="";
		} catch (Exception e) {
			Messages.addGlobalError("Error creating category with exception: {0}", e.getMessage());
			//Messages.addGlobalWarn("Create Category was not successful");
		}
	}
}
