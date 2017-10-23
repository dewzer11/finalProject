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
				" SELECT new northwind.report.CategorySales1997(c.categoryName, SUM(od.unitPrice * od.quantity * (1-od.discount)) As TotalSales)" 
				+" FROM OrderDetail od, IN (od.product) p, IN (p.category) c, IN (od.order) o"
				+" WHERE year(o.shippedDate) = 1997"
				+" GROUP BY c.categoryName "
				+" ORDER BY c.categoryName ",
				CategorySales1997.class)
				.getResultList();		
	}

}