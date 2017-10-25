package northwind.data;

import java.util.List;

import northwind.model.Employee;
import northwind.report.EmployeeSales1997;

public class EmployeeRepository extends AbstractJpaRepository<Employee> {
	private static final long serialVersionUID = 1L;

	public EmployeeRepository() {
		super(Employee.class);
	}
	
	public List<EmployeeSales1997> findEmployeeSales() {
		return getEntityManager().createQuery(
				" SELECT new northwind.report.EmployeeSales1997(e.firstName, e.lastName, SUM(od.unitPrice * od.quantity * (1-od.discount)) As TotalSales)" 
				+" FROM OrderDetail od, IN (od.order) o, IN (o.employee) e "
				+" WHERE year(o.shippedDate) = 1997 "
				+" GROUP BY e.firstName, e.lastName "
				+" ORDER BY TotalSales desc ",
				EmployeeSales1997.class)
				.getResultList();		
	}

}