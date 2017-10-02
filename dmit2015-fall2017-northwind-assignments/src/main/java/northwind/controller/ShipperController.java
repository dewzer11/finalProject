package northwind.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import northwind.data.ShipperRepository;
import northwind.model.Shipper;

	@Model
	public class ShipperController {

		@Inject
		private ShipperRepository shipperRepository;
		
		private List<Shipper> shippers;
		
		@PostConstruct
		void init() {
			shippers = shipperRepository.findAll();
		}

		public List<Shipper> getCustomers() {
			return shippers;
		}

}
