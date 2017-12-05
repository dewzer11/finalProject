package northwind.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import northwind.data.OrderRepository;
import northwind.exception.AlreadyShippedException;
import northwind.exception.IllegalQuantityException;
import northwind.exception.NoInvoiceLinesException;
import northwind.model.Customer;
import northwind.model.Employee;
import northwind.model.Order;
import northwind.model.OrderDetail;
import northwind.model.OrderDetailPK;
import northwind.model.Product;

@Stateless
public class OrderService {

	@Inject
	private OrderRepository orderRepository;
	
	@Resource
	private EJBContext context;
	
	@Inject
	private EntityManager entityManager;
	
	public void createOrder(Order currentOrder) {
		orderRepository.persist(currentOrder);
	}
	
	public int createNewOrder(Customer customer, Employee employee, 
								Date requiredDate, Date orderDate, String shipAddress,
								String shipCity, String shipCountry, String shipName, String shipPostalCode,
								String shipRegion, List<OrderDetail>items) throws IllegalQuantityException, NoInvoiceLinesException{
		int orderId = 0;
		
		if (items == null || items.size() == 0) {
			context.setRollbackOnly();
			throw new NoInvoiceLinesException("There are no items in the order");			
		}
		
		// Create a new Order entity and persist the entity
		Order newOrder = new Order();

		newOrder.setCustomer(customer);
		newOrder.setEmployee(employee);
		newOrder.setRequiredDate(requiredDate);
		newOrder.setOrderDate(orderDate);
		newOrder.setShipAddress(shipAddress);
		newOrder.setShipCity(shipCity);
		newOrder.setShipCountry(shipCountry);
		newOrder.setShipName(shipName);
		newOrder.setShipPostalCode(shipPostalCode);
		newOrder.setShipRegion(shipRegion);
		
		
		
		orderRepository.persist(newOrder);	
		orderId = newOrder.getOrderID();
		
		for (OrderDetail singleItem : items) {
			// rollback the transaction if quantity is less than one
			if (singleItem.getQuantity() < 1) {
				context.setRollbackOnly();
				throw new IllegalQuantityException("Invalid quantity ordered.");
			}
			
			OrderDetailPK id = new OrderDetailPK();
			id.setOrderID(orderId);
			id.setProductID(singleItem.getProduct().getProductID());
			
			
			singleItem.setId(id);
			
			
			// set the invoice of each InvoiceLine
			singleItem.setOrder(newOrder);
			// persist the InvoiceLine
			entityManager.persist(singleItem);
		}		
		
		return orderId;
	}
	
	public void updateOrder(Order currentOrder) {
		orderRepository.persist(currentOrder);
	}
	
	public void removeOrder(Order currentOrder) {
		orderRepository.persist(currentOrder);
	}
	
	public Order findOneOrder(int orderID) {
		return orderRepository.findOne(orderID);
	}
	

	public void CancelOrder(Order currentOrder)
			throws AlreadyShippedException 		 {
		if(currentOrder.getShippedDate() != null) {
			throw new AlreadyShippedException("This order has already been shipped");
		}
		else {
			for (OrderDetail item : currentOrder.getOrderDetails()) {
				entityManager.remove(entityManager.merge(item));
			}
			entityManager.remove(entityManager.merge(currentOrder));
		}
	}

	/////////////////////////////////////////////////////////////COMPLETE ORDER SERVICE STUFF//////////////////////////////////////
	public void completeOrder(Order existingOrder)
			throws NoInvoiceLinesException, IllegalQuantityException {
		
		if(existingOrder.getShippedDate() == null) {
			throw new IllegalQuantityException("Need Shipping date.");
			// since i added this, if there is a shipped date and you put an order that has already been completed, it will complete again.
		}else {
		
		for(OrderDetail singleItem : existingOrder.getOrderDetails()) {
			
			if ((singleItem.getQuantity() > singleItem.getProduct().getUnitsInStock()) || (singleItem.getQuantity() < 1) ) {
				context.setRollbackOnly();
				throw new IllegalQuantityException("Invalid quantity ordered.");
			}else {
				//update units in stock of the product
				short unitsInStock=(short)(singleItem.getProduct().getUnitsInStock() - singleItem.getQuantity());
				singleItem.getProduct().setUnitsInStock(unitsInStock);
				entityManager.merge(singleItem.getProduct());	
			}
			
		}
		
		entityManager.merge(existingOrder);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
}
