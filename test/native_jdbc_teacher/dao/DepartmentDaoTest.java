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


import native_jdbc_teacher.ds.MySqlDataSource;
import native_jdbc_teacher.dto.Department;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepartmentDaoTest {
	private static Logger logger = LogManager.getLogger();
	private Connection con;
	private static DepartmentDao dao;	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		logger.debug("setUpBeforeClass()");
		dao = DepartmentDaoImpl.getInstance();
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
	public void test01SelectDepartmentByAll() {
		logger.debug("test01SelectDepartmentByAll()");
		try {
			List<Department> lists = dao.selectDepartmentByAll(con);
			Assert.assertNotEquals(-1, lists.size());
			for(Department d : lists) {
				logger.trace(d);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void test02InsertDepartment() {
		logger.debug("testInsertDepartment()");
		fail("Not yet implemented");
	}

	@Test
	public void test03UpdateDepartment() {
		logger.debug("testUpdateDepartment()");
		fail("Not yet implemented");
	}

	@Test
	public void test04DeleteDepartment() {
		logger.debug("testDeleteDepartment()");
		fail("Not yet implemented");
	}

}
