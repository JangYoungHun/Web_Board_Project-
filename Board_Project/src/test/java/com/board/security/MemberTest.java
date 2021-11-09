package com.board.security;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zaxxer.hikari.HikariDataSource;

import lombok.Setter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/security-context.xml"
		})
public class MemberTest {
	
	@Setter(onMethod_ = {@Autowired})
	private PasswordEncoder pwEncoder;
	
	@Setter(onMethod_ = {@Autowired})
	private HikariDataSource dataSource;
	
//	@Test
//	public void testInsertMember() {
//		String sql = "insert into tbl_member(userid, userpw, username) values (?,?,?)";
//		
//		for(int i =0; i< 100; i++) {
//			Connection con = null;
//			PreparedStatement pstmt =null;
//			
//			try {
//				con = dataSource.getConnection();
//				pstmt = con.prepareStatement(sql);
//				
//				pstmt.setString(2, pwEncoder.encode("pw"+i));
//				
//				if(i<80) {
//					pstmt.setString(1, "user" + i);
//					pstmt.setString(3, "일반사용자" + i);
//				}else if(i<90){
//					pstmt.setString(1, "manager" + i);
//					pstmt.setString(3, "운영자" + i);
//				}else {
//					pstmt.setString(1, "admin" + i);
//					pstmt.setString(3, "관리자" + i);
//				}
//				
//				pstmt.executeUpdate();
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			finally {	
//					try {
//						if(con != null)
//							con.close();
//						if(pstmt != null)
//							pstmt.close();
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//			}
//		}
//	}
	
	@Test
	public void testInsertAuth() {
		String sql = "insert into tbl_member_auth(userid, auth) values (?,?)";
		
		for(int i =0; i< 100; i++) {
			Connection con = null;
			PreparedStatement pstmt =null;
			
			try {
				con = dataSource.getConnection();
				pstmt = con.prepareStatement(sql);

				
				if(i<80) {
					pstmt.setString(1, "user" + i);
					pstmt.setString(2, "ROLE_USER" );
				}else if(i<90){
					pstmt.setString(1, "manager" + i);
					pstmt.setString(2, "ROLE_MANAGER");
				}else {
					pstmt.setString(1, "admin" + i);
					pstmt.setString(2, "ROLE_ADMIN");
				}
				
				pstmt.executeUpdate();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {	
					try {
						if(con != null)
							con.close();
						if(pstmt != null)
							pstmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		
		
	}
}
