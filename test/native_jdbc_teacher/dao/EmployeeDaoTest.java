package native_jdbc_teacher.dao;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import native_jdbc_teacher.daoimpl.EmployeeDaoImpl;
import native_jdbc_teacher.ds.MySqlDataSource;
import native_jdbc_teacher.dto.Department;
import native_jdbc_teacher.dto.Employee;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeDaoTest {
	private static Logger logger = LogManager.getLogger();
	private Connection con;
	private static EmployeeDao dao;	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		logger.debug("setUpBeforeClass()");
		dao = EmployeeDaoImpl.getInstance();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		logger.debug("tearDownAfterClass()");
		dao = null;
	}

	@Before
	public void setUp() throws Exception {
		logger.debug("setUp()");
		con = MySqlDataSource.getConnection();
	}

	@After
	public void tearDown() throws Exception {
		logger.debug("tearDown()");
		con.close();
	}

	@Test
	public void test01SelectEmployeeByEmpNo() {
		logger.debug("test01SelectEmployeeByEmpNo()");
		Employee emp = new Employee(1003);
		try {
			Employee selectedEmp = dao.selectEmployeeByEmpNo(con, emp);
			Assert.assertNotNull(selectedEmp);
			logger.trace(selectedEmp);
		}catch(RuntimeException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	public void test02SelectEmployeeByAll() throws SQLException {
		logger.debug("test02SelectEmployeeByAll()");
		List<Employee> lists = dao.selectEmployeeByAll(con);
		Assert.assertNotEquals(0, lists.size());
		for(Employee e: lists) logger.trace(e);
	}

	@Test
	public void test03SelectEmployeeGroupByDno() {
		logger.debug("test03SelectEmployeeGroupByDno()");
		Department dept = new Department();
		dept.setDeptNo(2);
		List<Employee> lists;
		try {
			lists = dao.selectEmployeeGroupByDno(con, dept);
			Assert.assertNotEquals(0, lists.size());
			for(Employee e: lists) logger.trace(e);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test06DeleteEmployee() {
		logger.debug("test06DeleteEmployee()");
		fail("Not yet implemented");
	}

	@Test
	public void test04InsertEmployee() {
		logger.debug("test04InsertEmployee()");
		fail("Not yet implemented");
	}

	@Test
	public void test05UpdateEmployee() {
		logger.debug("test05UpdateEmployee()");
		fail("Not yet implemented");
	}

}
