package northwind.controller;

import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.servlet.http.Part;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.omnifaces.util.Messages;

import northwind.data.CategoriesRepository;
import northwind.model.Category;
import northwind.report.CategorySales1997;
import northwind.service.CategoryService;

@Model
public class CategoriesController {
	
	private Part uploadedFile;	// +getter +setter

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
	
	@Inject
	private CategoryService categoryService;
	
	@NotBlank(message="Category Name value is required")
	@Length(min=3, max=20, message="Name must be between 3 and 20 characters")
	private String categoryName; // +getter+setter


	private String description; // +getter+setter
	private byte[] picture; // +getter+setter
	
	public void createNewCategory() {
		byte[] picture = null;
		if (uploadedFile != null) {
			long size = uploadedFile.getSize();
			InputStream content;
			try {
				content = uploadedFile.getInputStream();
				picture = new byte[(int) size];
				content.read(picture);				
			} catch(Exception e) {
				Messages.addGlobalError("File upload was not successful.");
			}
		}
		try	{
			categoryService.createCategory(categoryName, description, picture);
			Messages.addGlobalError("Create Category was successful");
			categoryName="";
			description="";
		} catch (Exception e) {
			Messages.addGlobalError("Error creating category with exception: {0}", e.getMessage());
			//Messages.addGlobalWarn("Create Category was not successful");
		}
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Part getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

}
