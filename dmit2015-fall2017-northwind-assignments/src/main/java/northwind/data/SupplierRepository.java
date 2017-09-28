package northwind.data;

import northwind.model.Supplier;

public class SupplierRepository extends AbstractJpaRepository<Supplier> {
	private static final long serialVersionUID = 1L;

	public SupplierRepository() {
		super(Supplier.class);
		
	}
}