package northwind.data;

import static org.junit.Assert.*;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import northwind.model.Product;
import northwind.report.CategorySales1997;
import northwind.report.EmployeeSales1997;
//import northwind.report.CategorySalesForYear;
//import northwind.report.EmployeeSalesForYear;
//import northwind.report.MonthlySales;
//import northwind.report.ProductSalesForYear;
//import northwind.report.TopCustomer;
import northwind.util.Resources;

@RunWith(Arquillian.class)
public class Assignment03Test {

	@Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
        		.addPackage("northwind.model")
        		.addPackage("northwind.report")
        		.addPackage("northwind.data")
                .addClasses(Resources.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                // Deploy our test datasource
                .addAsWebInfResource("test-ds.xml");
    }

	@Inject
	private CategoriesRepository categoryRepository;
	
	@Inject
	private ProductRepository productRepository;
	
	@Inject
	private OrderRepository orderRepository;
	
	@Inject
	private EmployeeRepository employeeRepository;
	
	@Inject
	private CustomerRepository customerRepository;
	
	@Test
	public void shouldFindTenMostExpensiveProducts() {
		List<Product> queryResults = productRepository.findTenMostExpensiveProducts();
		assertEquals(10, queryResults.size());
		
		// verify the first, last, and middle result
		Product firstResult = queryResults.get(0);
		assertEquals("Cte de Blaye", firstResult.getProductName());
		assertEquals(263.50, firstResult.getUnitPrice().doubleValue(), 0);
		
		Product lastResult = queryResults.get(queryResults.size() - 1);
		assertEquals("Rssle Sauerkraut", lastResult.getProductName());
		assertEquals(45.60, lastResult.getUnitPrice().doubleValue(), 0);

		Product middleResult = queryResults.get(queryResults.size()/2 - 1);
		assertEquals("Carnarvon Tigers", middleResult.getProductName());
		assertEquals(62.50, middleResult.getUnitPrice().doubleValue(), 0);

	}
	
	@Test
	public void shouldFindCategorySalesForYear() {
		List<CategorySales1997> queryResults = categoryRepository.findCategorySales();
		assertEquals(8, queryResults.size());
		
		// verify the first, last, and middle result
		CategorySales1997 firstResult = queryResults.get(0);
		assertEquals("Beverages", firstResult.getCategoryName());
		assertEquals(102074.31, firstResult.getTotalSales().doubleValue(), 0);
		
		CategorySales1997 lastResult = queryResults.get(queryResults.size() - 1);
		assertEquals("Seafood", lastResult.getCategoryName());
		assertEquals(65544.19, lastResult.getTotalSales().doubleValue(), 0);
		
		CategorySales1997 middleResult = queryResults.get(queryResults.size()/2 - 1);
		assertEquals("Dairy Products", middleResult.getCategoryName());
		assertEquals(114749.77, middleResult.getTotalSales().doubleValue(), 0);
	}
	
//	@Test
//	public void shouldFindMonthlySalesByYear() {
//		List<MonthlySales> salesFor1996 = orderRepository.findMonthSales(1996);
//		assertEquals(12, salesFor1996.size());
//		
//		List<MonthlySales> salesFor1997 = orderRepository.findMonthSales(1997);
//		assertEquals(12, salesFor1997.size());
//
//		List<MonthlySales> salesFor1998 = orderRepository.findMonthSales(1998);
//		assertEquals(12, salesFor1998.size());
//
//		// verify months with zero sales
//		for(int month = 1; month <= 6; month++) {
//			assertEquals(0, salesFor1996.get(month - 1).getMonthAmount().doubleValue(), 0);
//		}
//		for(int month = 6; month <= 12; month++) {
//			assertEquals(0, salesFor1998.get(month - 1).getMonthAmount().doubleValue(), 0);
//		}
//		
//		// verify the first and last result for 1996
//		MonthlySales month7Year1996 = salesFor1996.get(7 - 1);
//		assertEquals(7, month7Year1996.getMonth());
//		assertEquals(20710.27, month7Year1996.getMonthAmount().doubleValue(), 0);
//		
//		MonthlySales month12Year1996 = salesFor1996.get(12 - 1);
//		assertEquals(12, month12Year1996.getMonth());
//		assertEquals(46178.78, month12Year1996.getMonthAmount().doubleValue(), 0);
//		
//		
//		// verify the first, last, and middle result for 1997
//		MonthlySales firstMonth1997 = salesFor1997.get(0);
//		assertEquals(1, firstMonth1997.getMonth());
//		assertEquals(64746.46, firstMonth1997.getMonthAmount().doubleValue(), 0);
//		
//		MonthlySales halfYear1997 = salesFor1997.get(salesFor1997.size()/2 - 1);
//		assertEquals(6, halfYear1997.getMonth());
//		assertEquals(50083.00, halfYear1997.getMonthAmount().doubleValue(), 0);
//		
//		MonthlySales lastMonth1997 = salesFor1997.get(salesFor1997.size() - 1);
//		assertEquals(12, lastMonth1997.getMonth());
//		assertEquals(60420.27, lastMonth1997.getMonthAmount().doubleValue(), 0);
//		
//		// verify the first and last result for 1998
//		MonthlySales month1Year1998 = salesFor1998.get(1 - 1);
//		assertEquals(1, month1Year1998.getMonth());
//		assertEquals(83651.59, month1Year1998.getMonthAmount().doubleValue(), 0);
//		
//		MonthlySales month5Year1998 = salesFor1998.get(5 - 1);
//		assertEquals(5, month5Year1998.getMonth());
//		assertEquals(18460.28, month5Year1998.getMonthAmount().doubleValue(), 0);
//	}
//	
//	@Test
//	public void shouldFindProductSalesForYear() {
//		List<ProductSalesForYear> queryResults = productRepository.findProductSalesForYear(1997);
//		assertEquals(77, queryResults.size());
//		
//		// verify the first, middle, and last result 
//		ProductSalesForYear firstResult = queryResults.get(0);
//		assertEquals("Beverages", firstResult.getCategoryName());
//		assertEquals("Cte de Blaye", firstResult.getProductName());
//		assertEquals(46563.09, firstResult.getProductSales().doubleValue(), 0);
//		
//		ProductSalesForYear middleResult = queryResults.get(queryResults.size() / 2 - 1);
//		assertEquals("Beverages", middleResult.getCategoryName());
//		assertEquals("Steeleye Stout", middleResult.getProductName());
//		assertEquals(5706.90, middleResult.getProductSales().doubleValue(), 0);		
//		
//		ProductSalesForYear lastResult = queryResults.get(queryResults.size() - 1);
//		assertEquals("Condiments", lastResult.getCategoryName());
//		assertEquals("Chef Anton's Gumbo Mix", lastResult.getProductName());
//		assertEquals(373.63, lastResult.getProductSales().doubleValue(), 0);
//	}
//	
	@Test
	public void shouldFindEmployeeSalesForYear() {
		List<EmployeeSales1997> queryResults = employeeRepository.findEmployeeSales();
		assertEquals(9, queryResults.size());
		
		// verify all results
		EmployeeSales1997 result1 = queryResults.get(0);
		assertEquals("Margaret Peacock", result1.getEmployeeName());
		assertEquals(124655.57, result1.getTotalSales().doubleValue(), 0);
		
		EmployeeSales1997 result2 = queryResults.get(1);
		assertEquals("Janet Leverling", result2.getEmployeeName());
		assertEquals(103719.09, result2.getTotalSales().doubleValue(), 0);
		
		EmployeeSales1997 result3 = queryResults.get(2);
		assertEquals("Nancy Davolio", result3.getEmployeeName());
		assertEquals(95850.39, result3.getTotalSales().doubleValue(), 0);
		
		EmployeeSales1997 result4 = queryResults.get(3);
		assertEquals("Andrew Fuller", result4.getEmployeeName());
		assertEquals(71168.14, result4.getTotalSales().doubleValue(), 0);
		
		EmployeeSales1997 result5 = queryResults.get(4);
		assertEquals("Robert King", result5.getEmployeeName());
		assertEquals(59827.20, result5.getTotalSales().doubleValue(), 0);
		
		EmployeeSales1997 result6 = queryResults.get(5);
		assertEquals("Laura Callahan", result6.getEmployeeName());
		assertEquals(56954.04, result6.getTotalSales().doubleValue(), 0);
		
		EmployeeSales1997 result7 = queryResults.get(6);
		assertEquals("Michael Suyama", result7.getEmployeeName());
		assertEquals(40826.37, result7.getTotalSales().doubleValue(), 0);
		
		EmployeeSales1997 result8 = queryResults.get(7);
		assertEquals("Steven Buchanan", result8.getEmployeeName());
		assertEquals(31433.19, result8.getTotalSales().doubleValue(), 0);

		EmployeeSales1997 result9 = queryResults.get(8);
		assertEquals("Anne Dodsworth", result9.getEmployeeName());
		assertEquals(24412.89, result9.getTotalSales().doubleValue(), 0);
				
	}
//	
//	@Test
//	public void shouldFindTop5Customers() {
//		List<TopCustomer> queryResults = customerRepository.findTopCustomers(5);
//		assertEquals(5, queryResults.size());
//		
//		// verify all results 
//		TopCustomer result1 = queryResults.get(0);
//		assertEquals("QUICK-Stop", result1.getCustomerName());
//		assertEquals("QUICK", result1.getCustomerID());
//		assertEquals(110277.31, result1.getCustomerSales().doubleValue(),0);
//		
//		TopCustomer result2 = queryResults.get(1);
//		assertEquals("Ernst Handel", result2.getCustomerName());
//		assertEquals("ERNSH", result2.getCustomerID());
//		assertEquals(104874.98, result2.getCustomerSales().doubleValue(),0);
//		
//		TopCustomer result3 = queryResults.get(2);
//		assertEquals("Save-a-lot Markets", result3.getCustomerName());
//		assertEquals("SAVEA", result3.getCustomerID());
//		assertEquals(104361.95, result3.getCustomerSales().doubleValue(),0);
//		
//		TopCustomer result4 = queryResults.get(3);
//		assertEquals("Rattlesnake Canyon Grocery", result4.getCustomerName());
//		assertEquals("RATTC", result4.getCustomerID());
//		assertEquals(51097.80, result4.getCustomerSales().doubleValue(),0);
//		
//		TopCustomer result5 = queryResults.get(queryResults.size() - 1);
//		assertEquals("Hungry Owl All-Night Grocers", result5.getCustomerName());
//		assertEquals("HUNGO", result5.getCustomerID());
//		assertEquals(49979.91, result5.getCustomerSales().doubleValue(), 0);	
//	}
//	
//	@Test
//	public void shouldFindTop25PercentCustomers() {
//		List<TopCustomer> queryResults = customerRepository.findTopPercentCustomers(25);
//		assertEquals(23, queryResults.size());
//		
//		// verify first, middle, and last result
//		TopCustomer firstResult = queryResults.get(0);
//		assertEquals("Berglunds snabbkp", firstResult.getCustomerName());
//		assertEquals("BERGS", firstResult.getCustomerID());
//		assertEquals(24927.58, firstResult.getCustomerSales().doubleValue(), 0);
//		
//		TopCustomer middleResult = queryResults.get(queryResults.size()/2 - 1);
//		assertEquals("Hungry Owl All-Night Grocers", middleResult.getCustomerName());
//		assertEquals("HUNGO", middleResult.getCustomerID());
//		assertEquals(49979.91, middleResult.getCustomerSales().doubleValue(), 0);
//		
//		TopCustomer lastResult = queryResults.get(queryResults.size() - 1);
//		assertEquals("White Clover Markets", lastResult.getCustomerName());
//		assertEquals("WHITC", lastResult.getCustomerID());
//		assertEquals(27363.61, lastResult.getCustomerSales().doubleValue(), 0);	
//	}

}
