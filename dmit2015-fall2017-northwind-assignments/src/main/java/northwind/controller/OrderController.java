package northwind.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.omnifaces.util.Messages;

import northwind.data.OrderRepository;
import northwind.model.Order;

@Model
public class OrderController {
	
	@Inject
	private OrderRepository orderRepository;
	
	private List<Order> orders;
	
	@PostConstruct
	void init() {
		orders = orderRepository.findAll();
	}
	
	public List<Order> getOrders() {
		return orders;
	}
	
	
	private List<Order> ordersByCustomer;	// getter
	private String currentSelectedCustomerId;	// getter/setter
	
	public void findOrderByCustomer() {
		if( !FacesContext.getCurrentInstance().isPostback() ) {
			// verify that a valid genreId was set
			if( currentSelectedCustomerId != null && !currentSelectedCustomerId.isEmpty()) {
				ordersByCustomer = orderRepository.findAllByCustomerId(currentSelectedCustomerId);
				if( ordersByCustomer.size() == 0 ) {
					Messages.addGlobalInfo("There are no orders for customerID {0}", 
							currentSelectedCustomerId);
				}
			} else {
				Messages.addGlobalError("Bad request. A valid customerID is required.");
			}
		}
		}
		
	public String getCurrentSelectedCustomerId() {
		return currentSelectedCustomerId;
	}

	public void setCurrentSelectedGenreId(String currentSelectedCustomerId) {
		this.currentSelectedCustomerId = currentSelectedCustomerId;
	}

	public List<Order> ordersByCustomer() {
		return ordersByCustomer;
	}

	public void setCurrentSelectedCustomerId(String currentSelectedCustomerId) {
		this.currentSelectedCustomerId = currentSelectedCustomerId;
	}
		
		
	//Orders by Employee
	private List<Order> ordersByEmployee;	// getter
	private int currentSelectedEmployeeId;	// getter/setter
		
	public int getCurrentSelectedEmployeeId() {
		return currentSelectedEmployeeId;
	}

	public void setCurrentSelectedEmployeeId(int currentSelectedEmployeeId) {
		this.currentSelectedEmployeeId = currentSelectedEmployeeId;
	}

	public List<Order> getOrdersByEmployee() {
		return ordersByEmployee;
	}
		
		public void findOrderByEmployee() {
			if( !FacesContext.getCurrentInstance().isPostback() ) {
				// verify that a valid genreId was set
				if( currentSelectedEmployeeId > 0) {
					ordersByEmployee = orderRepository.findAllByEmployeeId(currentSelectedEmployeeId);
					if( ordersByEmployee.size() == 0 ) {
						Messages.addGlobalInfo("There are no orders for employeeID {0}", 
								currentSelectedEmployeeId);
					}
				} else {
					Messages.addGlobalError("Bad request. A valid employeeID is required.");
				}
			}
			}
	}