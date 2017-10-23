package northwind.data;

import java.util.List;


import northwind.model.Product;

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
	
	public List<Product> findTenMostExpensiveProducts() {
		return getEntityManager().createQuery(
						"SELECT new northwind.report.Product (productName unitPrice " 
						+ " FROM Products "  
						+ " ORDER BY unitPrice DESC " 
						+ " LIMIT 10 ",
						Product.class)
				.getResultList();
	}	
}
