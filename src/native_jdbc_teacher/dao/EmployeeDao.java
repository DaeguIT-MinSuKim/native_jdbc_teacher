package native_jdbc_teacher.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import native_jdbc_teacher.dto.Department;
import native_jdbc_teacher.dto.Employee;

public interface EmployeeDao {
	Employee selectEmployeeByDno(Connection con, Department dept) throws SQLException;
	
	List<Employee> selectEmployeeByAll(Connection con) throws SQLException;
	
	List<Employee> selectEmployeeGroupByDno(Connection con, Department dept) throws SQLException;
}
