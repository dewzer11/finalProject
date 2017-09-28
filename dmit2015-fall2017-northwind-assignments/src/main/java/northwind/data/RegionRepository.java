package northwind.data;

import northwind.model.Region;

public class RegionRepository extends AbstractJpaRepository<Region> {
	private static final long serialVersionUID = 1L;

	public RegionRepository() {
		super(Region.class);
		
	}
}