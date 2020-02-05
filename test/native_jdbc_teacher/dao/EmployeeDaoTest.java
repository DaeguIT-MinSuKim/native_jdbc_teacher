package native_jdbc_teacher.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import native_jdbc_teacher.LogUtil;
import native_jdbc_teacher.daoimpl.EmployeeDaoImpl;
import native_jdbc_teacher.ds.MySqlDataSource;
import native_jdbc_teacher.dto.Department;
import native_jdbc_teacher.dto.Employee;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeDaoTest {
	private Connection con;
	private static EmployeeDao dao;
	private static File imagesDir;
	private static File picsDir;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		LogUtil.prnLog("setUpBeforeClass()");
		dao = EmployeeDaoImpl.getInstance();
		picsDir = new File(System.getProperty("user.dir") + File.separator + "pics" + File.separator);
		if (!picsDir.exists()) {
			picsDir.mkdir();
		}
		imagesDir = new File(System.getProperty("user.dir") + File.separator + "images" + File.separator);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		LogUtil.prnLog("tearDownAfterClass()");
		dao = null;
	}

	@Before
	public void setUp() throws Exception {
		LogUtil.prnLog("setUp()");
		con = MySqlDataSource.getConnection();
	}

	@After
	public void tearDown() throws Exception {
		LogUtil.prnLog("tearDown()");
		con.close();
	}

	@Test
	public void test01SelectEmployeeByEmpNo() {
		LogUtil.prnLog("test01SelectEmployeeByEmpNo()");
		Employee emp = new Employee(1004);
		try {
			Employee selectedEmp = dao.selectEmployeeByEmpNo(con, emp);
			if (selectedEmp.getPic() != null) {
				getImageToPic(selectedEmp.getPic(), selectedEmp.getEmpNo());// 프로젝트 폴더의 pics폴더에 사원번호.jpg파일이 생성
			}
			Assert.assertNotNull(selectedEmp);
			LogUtil.prnLog(selectedEmp);
		} catch (RuntimeException e) {
			LogUtil.prnLog(e.getMessage());
			e.printStackTrace();
		}
	}

	private void getImageToPic(byte[] pic, int empNo) {
		File file = new File(picsDir, empNo + ".jpg");
		try (FileOutputStream fis = new FileOutputStream(file)) {
			fis.write(pic);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test02SelectEmployeeByAll() throws SQLException {
		LogUtil.prnLog("test02SelectEmployeeByAll()");
		List<Employee> lists = dao.selectEmployeeByAll(con);
		Assert.assertNotEquals(0, lists.size());
		for (Employee e : lists)
			LogUtil.prnLog(e);
	}

	@Test
	public void test03SelectEmployeeGroupByDno() {
		LogUtil.prnLog("test03SelectEmployeeGroupByDno()");
		Department dept = new Department();
		dept.setDeptNo(2);
		List<Employee> lists;
		try {
			lists = dao.selectEmployeeGroupByDno(con, dept);
			Assert.assertNotEquals(0, lists.size());
			for (Employee e : lists)
				LogUtil.prnLog(e);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test06DeleteEmployee() {
		LogUtil.prnLog("test06DeleteEmployee()");
		Employee emp = new Employee(1004);
	    int res = dao.deleteEmployee(con, emp);
	    LogUtil.prnLog(res);
	    Assert.assertEquals(1, res);
	}

	@Test
	public void test04InsertEmployee() {
		LogUtil.prnLog("test04InsertEmployee()");
		Employee emp = new Employee(1004, "서현진", "사원", new Employee(1003), 1500000, new Department(1),
				getImage("seohyunjin.jpg"));
		LogUtil.prnLog(emp);
		int res = dao.insertEmployee(con, emp);
		Assert.assertEquals(1, res);
	}

	private byte[] getImage(String imgName) {
		File file = new File(imagesDir, imgName);
//		logger.debug(file.getAbsolutePath());
		try (InputStream is = new FileInputStream(file)) {
			byte[] pic = new byte[is.available()];
			is.read(pic);
			return pic;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Test
	public void test05UpdateEmployee() {
		LogUtil.prnLog("test05UpdateEmployee()");
		Employee emp = new Employee(1004, "이유영", "대리", new Employee(3426), 3500000, new Department(1));
		emp.setPic(getImage("lyy.jpg"));
		int res = dao.updateEmployee(con, emp);
		LogUtil.prnLog(res);
		Assert.assertEquals(1, res);
	}

}
