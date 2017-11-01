package northwind.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import northwind.data.CategoriesRepository;
import northwind.model.Category;

@Stateless
public class CategoryService {

	
	@Inject
	private CategoriesRepository categoriesRepository;
	
	public void createCategory(String categoryName) {
		Category currentCategory = new Category();
		currentCategory.setCategoryName(categoryName);
		createCategory(currentCategory);
	}
	
	public void createCategory (Category currentCategory) {
		categoriesRepository.persist(currentCategory);
	}
	
}
