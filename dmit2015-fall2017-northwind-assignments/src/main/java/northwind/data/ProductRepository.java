package northwind.data;

import java.util.List;


import northwind.model.Product;
import northwind.report.CategorySales1997;
import northwind.report.ProductSales1997;
import northwind.report.TenExpensiveProducts;

public class ProductRepository extends AbstractJpaRepository<Product> {
	private static final long serialVersionUID = 1L;

	public ProductRepository() {
		super(Product.class);
		
	}
	public List<Product> findAllByCategoryId(int categoryId) {
		return getEntityManager().createQuery(
			"SELECT prod FROM Product prod WHERE prod.category.categoryID = :idValue", Product.class)
			.setParameter("idValue", categoryId)
			.getResultList();
	}
	
	
	public List<ProductSales1997> findProductSales() {
		return getEntityManager().createQuery(
				" SELECT new northwind.report.ProductSales1997(c.productName, SUM(od.unitPrice * od.quantity * (1-od.discount)) As TotalSales)" 
				+" FROM OrderDetail od, IN (od.product) p, IN (p.category) c, IN (od.order) o"
				+" WHERE year(o.shippedDate) = 1997"
				+" GROUP BY c.productName "
				+" ORDER BY c.productName ",
				ProductSales1997.class)
				.getResultList();		
	}
	
	public List<TenExpensiveProducts> findTenExpensiveProducts() {
		return getEntityManager().createQuery(
				" SELECT new northwind.report.TenExpensiveProducts (productName, unitPrice) FROM Product ORDER BY unitPrice DESC ",
				TenExpensiveProducts.class)
				.setMaxResults(10)
				.getResultList();
		
	}

}
