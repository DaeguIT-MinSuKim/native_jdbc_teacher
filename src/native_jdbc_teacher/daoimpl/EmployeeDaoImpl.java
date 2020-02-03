package native_jdbc_teacher.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import native_jdbc_teacher.dao.EmployeeDao;
import native_jdbc_teacher.dto.Department;
import native_jdbc_teacher.dto.Employee;

public class EmployeeDaoImpl implements EmployeeDao {
	private static final EmployeeDaoImpl instance = new EmployeeDaoImpl();

	private EmployeeDaoImpl() {}

	public static EmployeeDaoImpl getInstance() {
		return instance;
	}

	@Override
	public Employee selectEmployeeByDno(Connection con, Department dept) throws SQLException {
		// TODO Auto-generated method stub
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
			while(rs.next()) {
				list.add(getEmployee(rs));
			}
		}
		return list;
	}

	private Employee getEmployee(ResultSet rs) throws SQLException {
		int empNo = rs.getInt("empno");
		String empName = rs.getString("empname");
		String title = rs.getString("title");
		Employee manager = new Employee(rs.getInt("manager"));
		int salary = rs.getInt("salary");
		Department dept = new Department();
		dept.setDeptNo(rs.getInt("dno"));
		return new Employee(empNo, empName, title, manager, salary, dept);
	}



}
