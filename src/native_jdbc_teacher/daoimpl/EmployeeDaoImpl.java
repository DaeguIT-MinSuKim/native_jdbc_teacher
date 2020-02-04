package native_jdbc_teacher.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import native_jdbc_teacher.dao.EmployeeDao;
import native_jdbc_teacher.dto.Department;
import native_jdbc_teacher.dto.Employee;

public class EmployeeDaoImpl implements EmployeeDao {
	private static Logger logger = LogManager.getLogger();
	
	private static final EmployeeDaoImpl instance = new EmployeeDaoImpl();

	private EmployeeDaoImpl() {}

	public static EmployeeDaoImpl getInstance() {
		return instance;
	}

	@Override
	public Employee selectEmployeeByEmpNo(Connection con, Employee emp) {
		String sql = "select empno, empname, title, manager, salary, dno, pic from employee where empno=?";
		try(PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setInt(1, emp.getEmpNo());
			logger.trace(pstmt);
			try(ResultSet rs = pstmt.executeQuery()){
				if (rs.next()) {
					return getEmployee(rs, true);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return null;
	}
	
	@Override
	public List<Employee> selectEmployeeGroupByDno(Connection con, Department dept) throws SQLException {
		String sql = "select e.empno, e.empname , e.title , m.empname as manager_name , m.empno as manager_no , e.salary , e.dno , d.deptname \r\n" + 
				     "  from employee e left join employee m on e.manager = m.empno join department d on e.dno = d.deptno\r\n" + 
				     " where e.dno =?";
		List<Employee> list = new ArrayList<>();
		try(PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setInt(1, dept.getDeptNo());
			System.out.println(pstmt);
			try(ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					list.add(getEmployeeFull(rs));
				}
			}
		}
		return list;
	}
	
	private Employee getEmployeeFull(ResultSet rs) throws SQLException {
		int empNo = rs.getInt("empno");
		String empName = rs.getString("empname");
		String title = rs.getString("title");
		Employee manager = new Employee(rs.getInt("manager_no"));
		manager.setEmpName(rs.getString("manager_name"));
		int salary = rs.getInt("salary");
		Department dept = new Department();
		dept.setDeptNo(rs.getInt("dno"));
		dept.setDeptName(rs.getString("deptname"));
		return new Employee(empNo, empName, title, manager, salary, dept);
	}

	@Override
	public List<Employee> selectEmployeeByAll(Connection con) throws SQLException {
		String sql = "select empno, empname, title, manager, salary, dno from employee";
		List<Employee> list = new ArrayList<>();
		try(PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()){
			logger.trace(pstmt);
			while(rs.next()) {
				list.add(getEmployee(rs, false));
			}
		}
		return list;
	}

	private Employee getEmployee(ResultSet rs, boolean isPic) throws SQLException {
		int empNo = rs.getInt("empno");
		String empName = rs.getString("empname");
		String title = rs.getString("title");
		Employee manager = new Employee(rs.getInt("manager"));
		int salary = rs.getInt("salary");
		Department dept = new Department();
		dept.setDeptNo(rs.getInt("dno"));
		Employee employee = new Employee(empNo, empName, title, manager, salary, dept);
		if (isPic) {
			employee.setPic(rs.getBytes("pic"));
		}
		return employee;
	}

	@Override
	public int deleteEmployee(Connection con, Employee employee) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertEmployee(Connection con, Employee employee) {
		String sql = null;
		
		if (employee.getPic() == null) {
			sql = "insert into employee(empno, empname, title, manager, salary, dno) "
					+ "values (?, ?, ?, ?, ?, ?)";
		}else {
			sql = "insert into employee values(?, ?, ?, ?, ?, ?, ?)";
		}
		
		logger.debug(sql);
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	        pstmt.setInt(1, employee.getEmpNo());
	        pstmt.setString(2, employee.getEmpName());
	        pstmt.setString(3, employee.getTitle());
	        pstmt.setInt(4, employee.getManager().getEmpNo());
	        pstmt.setInt(5, employee.getSalary());
	        pstmt.setInt(6, employee.getDept().getDeptNo());
	        if (employee.getPic()!=null) {
	            pstmt.setBytes(7, employee.getPic());
	        }
	        return pstmt.executeUpdate();
	    } catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public int updateEmployee(Connection con, Employee employee) {
		// TODO Auto-generated method stub
		return 0;
	}



}
