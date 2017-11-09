package northwind.controller;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.omnifaces.util.Messages;

import northwind.data.OrderRepository;
import northwind.model.Order;
import northwind.service.OrderService;

@Model
public class SalesByDateRangeController {
	
	@Inject
	private Logger log;
	
	@Inject
	private OrderRepository orderRepository;
	
	
	private List<Order> orders;
	
	private Date startDate;
	private Date endDate;
	
	public void findOrdersByDateRange() {
		try {
			orders = orderRepository.findByDateRange(startDate, endDate);
			if( orders.size() == 0 ) {
				Messages.addGlobalInfo("We found 0 results for orders between {0,date} and {1,date}",
						startDate, endDate);
			} else {
				Messages.addGlobalInfo("There are {0} orders from {1,date} to {2,date}", orders.size(), startDate, endDate);			
			}
		} catch(Exception e) {
			log.info(e.getMessage());
			orders = null;
			Messages.addGlobalInfo("This is your catch. Something went wrong!!!!!");
			}
		}
	
	
	
	
	public List<Order> getOrders() {
		return orders;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	
	}
	
	
	

