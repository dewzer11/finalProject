package northwind.data;

import northwind.model.Category;

public class CategoriesRepository extends AbstractJpaRepository<Category> {
	private static final long serialVersionUID = 1L;

	public CategoriesRepository() {
		super(Category.class);
	}

}