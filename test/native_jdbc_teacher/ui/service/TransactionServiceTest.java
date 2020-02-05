package native_jdbc_teacher.ui.service;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import native_jdbc_teacher.LogUtil;
import native_jdbc_teacher.dto.Department;
import native_jdbc_teacher.dto.Employee;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TransactionServiceTest {
	private static TransactionService service;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		service = new TransactionService();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		service = null;
	}

	@Test
	public void test01TransAddEmpAndDept_DeptFail() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		int res = 0;
		Department dept = new Department(1, "마케팅", 8);//존재하는 부서 
		Employee emp = new Employee(1004, "수지", "사원", new Employee(1003), 1500000, dept);
		
		res = service.transAddEmpAndDept(emp, dept);
		Assert.assertNotEquals(2, res);
	}

	@Test
	public void test02TransAddEmpAndDept_EmpFail() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		int res = 0;
		Department dept = new Department(5, "마케팅", 8);
		Employee emp = new Employee(4377, "수지", "사원", new Employee(1003), 1500000, dept);//존재하는 사원
		
		res = service.transAddEmpAndDept(emp, dept);
		Assert.assertNotEquals(2, res);
	}
	
	@Test
	public void test03TransAddEmpAndDept_Success() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Department dept = new Department(5, "마케팅", 8);
		Employee emp = new Employee(1004, "수지", "사원", new Employee(1003), 1500000, dept);
		
		int res = service.transAddEmpAndDept(emp, dept);
		Assert.assertEquals(2, res);
	}
	
	
	@Test
	public void test04TransAddEmpAndDeptWithConnection_DeptFail() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		int res = 0;
		Department dept = new Department(1, "마케팅", 8);//존재하는 부서 
		Employee emp = new Employee(1005, "이유영", "사원", new Employee(1003), 1500000, dept);
		
		res = service.transAddEmpAndDeptWithConnection(emp, dept);
		Assert.assertNotEquals(2, res);
	}

	@Test(expected = RuntimeException.class)
	public void test05TransAddEmpAndDeptWithConnection_EmpFail() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		int res = 0;
		Department dept = new Department(6, "마케팅6", 8);
		Employee emp = new Employee(4377, "이유영", "사원", new Employee(1003), 1500000, dept);//존재하는 사원
		
		res = service.transAddEmpAndDeptWithConnection(emp, dept);
		Assert.assertNotEquals(2, res);
	}
	
	@Test
	public void test06TransAddEmpAndDeptWithConnection_Success() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		
		Department dept = new Department(6, "마케팅6", 6);
		Employee emp = new Employee(1005, "이유영", "사원", new Employee(1003), 1500000, dept);
		
		int res = service.transAddEmpAndDeptWithConnection(emp, dept);
		Assert.assertEquals(2, res);
	}
}
