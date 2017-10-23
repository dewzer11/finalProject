package northwind.data;


import java.util.List;

import northwind.model.Category;
import northwind.report.CategorySales1997;

public class CategoriesRepository extends AbstractJpaRepository<Category> {
	private static final long serialVersionUID = 1L;

	public CategoriesRepository() {
		super(Category.class);
	}
	
	public List<CategorySales1997> findCategorySales() {
		return getEntityManager().createQuery(
				" SELECT new northwind.report.1997CategorySales(g.categoryName, SUM(od.unitPrice * od.quantity) As TotalSales)" 
				+" FROM OrderDetail od, IN (od.Products) p, IN (p.category) c"
				+" GROUP BY g.name "
				+" ORDER BY g.name ",
				CategorySales1997.class)
				.getResultList();		
	}

}