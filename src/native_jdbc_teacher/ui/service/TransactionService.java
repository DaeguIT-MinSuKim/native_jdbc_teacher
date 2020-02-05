package native_jdbc_teacher.ui.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import native_jdbc_teacher.LogUtil;
import native_jdbc_teacher.dao.DepartmentDao;
import native_jdbc_teacher.dao.EmployeeDao;
import native_jdbc_teacher.daoimpl.DepartmentDaoImpl;
import native_jdbc_teacher.daoimpl.EmployeeDaoImpl;
import native_jdbc_teacher.ds.MySqlDataSource;
import native_jdbc_teacher.dto.Department;
import native_jdbc_teacher.dto.Employee;

public class TransactionService {
	private String deptSql = "insert into department values(?, ?, ?)";
	private String empSql = "insert into employee(empno, empname, title, manager, salary, dno) values (?, ?, ?, ?, ?, ?)";
	
	public int transAddEmpAndDept (Employee emp, Department dept) {
		int res = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		try{
			con = MySqlDataSource.getConnection();
			con.setAutoCommit(false);
			//department
			pstmt = con.prepareStatement(deptSql);
			pstmt.setInt(1, dept.getDeptNo());
			pstmt.setString(2, dept.getDeptName());
			pstmt.setInt(3, dept.getFloor());
			LogUtil.prnLog(pstmt);
			res = pstmt.executeUpdate();
			//employee
			pstmt = con.prepareStatement(empSql);
			pstmt.setInt(1, emp.getEmpNo());
	        pstmt.setString(2, emp.getEmpName());
	        pstmt.setString(3, emp.getTitle());
	        pstmt.setInt(4, emp.getManager().getEmpNo());
	        pstmt.setInt(5, emp.getSalary());
	        pstmt.setInt(6, emp.getDept().getDeptNo());
	        LogUtil.prnLog(pstmt);
	        res += pstmt.executeUpdate();
	        
	        if (res == 2) {
	        	con.commit();
	        	LogUtil.prnLog("result " + res + " commit()");
	        }else {
	        	throw new SQLException();
	        }
		} catch (SQLException e) {
			try {
				con.rollback();
				LogUtil.prnLog("result " + res + " rollback()");
			} catch (SQLException e1) {
				e1.printStackTrace();
			} 
		} finally {
			try{
				con.setAutoCommit(true);
				pstmt.close();
				con.close();
			}catch(Exception e) {}
		}
		return res;
	}
	
	public void transAddEmpAndDeptWithConnection(Employee emp, Department dept) {
		DepartmentDao deptDao = DepartmentDaoImpl.getInstance();
		EmployeeDao empDao = EmployeeDaoImpl.getInstance();
		Connection con = null;
		
		try {
			con = MySqlDataSource.getConnection();
			con.setAutoCommit(false);
			deptDao.insertDepartment(con, dept);
			empDao.insertEmployee(con, emp);
			con.commit();
			con.setAutoCommit(true);
			LogUtil.prnLog("result commit()");
		} catch (RuntimeException e) {
			try{
				con.rollback();
				con.setAutoCommit(true);
				throw e;
			}catch(Exception e1) {}
			LogUtil.prnLog("result rollback()");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
