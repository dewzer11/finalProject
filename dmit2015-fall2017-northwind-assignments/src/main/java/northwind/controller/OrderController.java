package northwind.controller;


import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import northwind.data.OrderRepository;
import northwind.model.Order;
import northwind.model.OrderDetail;
import northwind.report.MonthlySalesByYear;
import northwind.service.OrderService;


@SuppressWarnings("serial")
@Named
@ViewScoped
public class OrderController implements Serializable {
	private int currentSelectedOrderId;		// getter/setter
	private Order currentSelectedOrder;	// getter
	
	public int getCurrentSelectedOrderId() {
		return currentSelectedOrderId;
	}

	public void setCurrentSelectedOrderId(int currentSelectedOrderId) {
		this.currentSelectedOrderId = currentSelectedOrderId;
	}

	public Order getCurrentSelectedOrder() {
		return currentSelectedOrder;
	}
	
	
	public void findOrder() {
		if( !FacesContext.getCurrentInstance().isPostback() ) {
			if( currentSelectedOrderId > 0 ) {
				currentSelectedOrder = orderRepository.findOne(currentSelectedOrderId);
				if( currentSelectedOrder == null ) {
					Messages.addGlobalInfo("There is no Order with OrderID {0}", 
							currentSelectedOrderId);
				} else {
					Messages.addGlobalInfo("Successfully retreived Order info.");
				}
			} else {
				Messages.addGlobalError("Bad request. A valid OrderID is required.");
			}
		}
		
	}
	
	public Double getSubtotal() {
		return orderRepository.findSubTotal(getCurrentSelectedOrderId());
	}
	
	public void findOneOrder() {
		currentSelectedOrder = orderRepository.findOne(currentSelectedOrderId);
		if( currentSelectedOrder == null ) {
			Messages.addGlobalInfo("There is no invoice with invoiceID {0}", currentSelectedOrderId);					
		} else {
			Messages.addGlobalInfo("We found 1 result with invoiceID {0}", currentSelectedOrderId);								
		}
	}
	
	public void findAllOrdersByCustomer() {
		ordersByCustomer = orderRepository.findAllByCustomerId(currentSelectedCustomerId);
		currentSelectedOrder = null;
		int resultCount = ordersByCustomer.size();
		if (ordersByCustomer.size() == 0) {
			Messages.addGlobalError("Unknown customerId \"{0}\". We found 0 results", currentSelectedCustomerId);
		} else {
			Messages.addGlobalInfo("We found {0} results.", resultCount);
		}
	}
	
	public void findOneOrder(int orderId) {
		currentSelectedOrderId = orderId;
		findOneOrder();
	}
	
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
				// verify that a valid EmployeeID was set
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
		
		
	
		public List<MonthlySalesByYear> retrieveMonthlySales() {
			return orderRepository.findMonthSales(1997);
		}
		public List<MonthlySalesByYear> retrieveMonthlySales96() {
				return orderRepository.findMonthSales(1996);
			}
		public List<MonthlySalesByYear> retrieveMonthlySales98() {
			return orderRepository.findMonthSales(1998);
		}	
		
	}



