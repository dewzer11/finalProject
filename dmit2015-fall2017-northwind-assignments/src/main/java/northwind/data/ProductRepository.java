package northwind.data;

import northwind.model.Product;

public class ProductRepository extends AbstractJpaRepository<Product> {
	private static final long serialVersionUID = 1L;

	public ProductRepository() {
		super(Product.class);
		
	}

}
