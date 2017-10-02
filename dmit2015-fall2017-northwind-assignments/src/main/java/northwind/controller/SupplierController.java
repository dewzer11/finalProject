package northwind.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import northwind.data.SupplierRepository;
import northwind.model.Supplier;

@Model
public class SupplierController {

	@Inject
	private SupplierRepository supplierRepository;
	
	private List<Supplier> suppliers;
	
	@PostConstruct
	void init() {
		suppliers = supplierRepository.findAll();
	}

	public List<Supplier> getCustomers() {
		return suppliers;
	}
	
}
