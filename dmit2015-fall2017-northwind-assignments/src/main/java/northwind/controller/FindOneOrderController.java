package northwind.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.omnifaces.util.Messages;

import northwind.model.Order;
import northwind.service.OrderService;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class FindOneOrderController implements Serializable {

	@Inject
	private OrderService orderService;
	
	@NotNull(message="OrderID field value is required")
	private Integer searchValue; // +getter+setter
	private Order querySingleResult; // +getter
	
	public void findOrder() {
		try {
			querySingleResult = orderService.findOneOrder(searchValue);
			Messages.addGlobalInfo("1 result for {0}", searchValue);
		} catch (Exception e) {
			querySingleResult = null;
			Messages.addGlobalInfo("We found 0 results for {0}", searchValue);
		
		}

	}
	
	
}
