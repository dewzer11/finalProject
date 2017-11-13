package northwind.controller;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.omnifaces.util.Messages;

import northwind.data.OrderRepository;
import northwind.model.Order;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class FindOneOrderController implements Serializable {

	@Inject
	private OrderRepository orderRepository;
	
	@NotNull(message="OrderID field value is required")
	private Integer searchValue; // +getter+setter
	private Order querySingleResult; // +getter
	
	@Inject
	private Logger log;

	public Integer getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(Integer searchValue) {
		this.searchValue = searchValue;
	}

	public Order getQuerySingleResult() {
		return querySingleResult;
	}

	public void setQuerySingleResult(Order querySingleResult) {
		this.querySingleResult = querySingleResult;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public void findOrder() {
		try {
			querySingleResult = orderRepository.findOne(searchValue);
			if (querySingleResult == null) {
				Messages.addGlobalInfo("0 results for {0}", searchValue);
			}
			else {
				Messages.addGlobalInfo("1 result for {0}", searchValue);
			}

		} catch (Exception e) {
			log.info(e.getMessage());
			querySingleResult = null;
			Messages.addGlobalInfo("We found 0 results for {0}", searchValue);
		
		}

	}
	
	
}
