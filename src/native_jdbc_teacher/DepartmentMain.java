package native_jdbc_teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import native_jdbc_teacher.ds.C3P0DataSource;
import native_jdbc_teacher.ds.DBCPDataSource;
import native_jdbc_teacher.ds.Hikari_DataSource2;
import native_jdbc_teacher.dto.Department;

public class DepartmentMain {

	public static void main(String[] args) {
		try (Connection con = Hikari_DataSource2.getConnection()){
			selectDepartment(con);
			insertDepartment(con);
			updateDepartment(con);
			deleteDepartment(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try (Connection con = C3P0DataSource.getConnection()){
			selectDepartment(con);
			insertDepartment(con);
			updateDepartment(con);
			deleteDepartment(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try (Connection con = DBCPDataSource.getConnection()){
			selectDepartment(con);
			insertDepartment(con);
			updateDepartment(con);
			deleteDepartment(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void selectDepartment(Connection con) {
		String sql = "select deptno, deptname, floor from department";
		try (Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {

			List<Department> deptList = new ArrayList<>();

			while (rs.next()) {
				deptList.add(getDepartment(rs));
			}

			for (Department d : deptList) {
				System.out.println(d);
			}
			
		} catch (SQLException e) {
			System.err.println("해당 데이터베이스가 존재하지 않거나 계정 및 비밀번호 확인 요망~~" + e.getErrorCode());
			e.printStackTrace();
		}
	}

	private static Department getDepartment(ResultSet rs) throws SQLException {
		int deptNo = rs.getInt("deptno");
		String deptName = rs.getString("deptname");
		int floor = rs.getInt("floor");
		return new Department(deptNo, deptName, floor);
	}
	
	
	private static void deleteDepartment(Connection con) {
		String deleteSql = "delete from department where deptno=?";
		try(PreparedStatement pstmt = con.prepareStatement(deleteSql)){
			pstmt.setInt(1, 5);
			System.out.println(pstmt);
			int res = pstmt.executeUpdate();
			System.out.println("삭제 성공" + res);
		} catch (SQLException e) {
			System.err.println("해당 데이터베이스가 존재하지 않거나 계정 및 비밀번호 확인 요망~~" + e.getErrorCode());
			if (e.getErrorCode() == 1062) {
				JOptionPane.showMessageDialog(null, "해당 부서번호가 이미 존재함");
			}
		}
	}

	private static void updateDepartment(Connection con) {
		String updateSql = "update department set deptname=?, floor=? where deptno = ?";
		try(PreparedStatement pstmt = con.prepareStatement(updateSql)){
			pstmt.setString(1, "마케팅");
			pstmt.setInt(2, 11);
			pstmt.setInt(3, 5);
			System.out.println(pstmt);
			int res = pstmt.executeUpdate();
			System.out.println("수정 성공" + res);
		} catch (SQLException e) {
			System.err.println("해당 데이터베이스가 존재하지 않거나 계정 및 비밀번호 확인 요망~~" + e.getErrorCode());
			if (e.getErrorCode() == 1062) {
				JOptionPane.showMessageDialog(null, "해당 부서번호가 이미 존재함");
			}
		}
	}

	private static void insertDepartment(Connection con) {
		String insertSql = "insert into department values(?,?,?)";
		try (PreparedStatement pstmt = con.prepareStatement(insertSql)){
			pstmt.setInt(1, 5);
			pstmt.setString(2, "총무");
			pstmt.setInt(3, 30);
			
			System.out.println("연결 성공" + pstmt);
			int res = pstmt.executeUpdate();
			System.out.println("res : " + res);
			
		} catch (SQLException e) {
			System.err.println("해당 데이터베이스가 존재하지 않거나 계정 및 비밀번호 확인 요망~~" + e.getErrorCode());
			if (e.getErrorCode() == 1062) {
				JOptionPane.showMessageDialog(null, "해당 부서번호가 이미 존재함");
			}
//			e.printStackTrace();
		}
	}



}
