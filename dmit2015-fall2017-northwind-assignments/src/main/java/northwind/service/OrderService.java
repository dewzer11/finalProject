package northwind.service;

import java.math.BigDecimal;
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
import northwind.model.Product;
import northwind.model.Shipper;

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
	
	public Order createNewOrder(Customer customer, Set<Product> orderProducts, Employee employee, 
								Date requiredDate, int orderId, Date orderDate, String shipAddress,
								String shipCity, String shipCountry, String shipName, String shipPostalCode,
								String shipRegion) {
		// Create a new Order entity and persist the entity
		Order newOrder = new Order();

		newOrder.setCustomer(customer);
		newOrder.setEmployee(employee);
		newOrder.setRequiredDate(requiredDate);
		newOrder.setOrderID(orderId);
		newOrder.setOrderDate(orderDate);
		newOrder.setShipAddress(shipAddress);
		newOrder.setShipCity(shipCity);
		newOrder.setShipCountry(shipCountry);
		newOrder.setShipName(shipName);
		newOrder.setShipPostalCode(shipPostalCode);
		newOrder.setShipRegion(shipRegion);
		orderRepository.persist(newOrder);	
		return newOrder;
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
	
//	public void CancelOrder(Order currentOrder)
//			throws AlreadyShippedException 		 {
//		if(currentOrder.getShippedDate() != null) {
//			throw new AlreadyShippedException("This order has already been shipped");
//		}
//		else {
//			for (Orderdetail item : items) {
//				
//			}
//			entityManager.remove(currentOrder);
//		}
//	}
//	
	/////////////////////////////////////////////////////////////COMPLETE ORDER SERVICE STUFF//////////////////////////////////////
	public void completeOrder(Order existingOrder, BigDecimal freight, Shipper shipper, Date shippedDate)
			throws NoInvoiceLinesException, IllegalQuantityException {
		existingOrder.setFreight(freight);
		existingOrder.setShipper(shipper);
		existingOrder.setShippedDate(shippedDate);
		
		
		
		for(OrderDetail singleItem : existingOrder.getOrderDetails()) {
			if (singleItem.getQuantity() > singleItem.getProduct().getUnitsInStock() || singleItem.getQuantity() < 1 ) {
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
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
}
