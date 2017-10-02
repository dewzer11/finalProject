package northwind.data;

import northwind.model.Territory;;;

public class TerritoryRepository extends AbstractJpaRepository<Territory> {
	private static final long serialVersionUID = 1L;

	public TerritoryRepository() {
		super(Territory.class);
	}

}