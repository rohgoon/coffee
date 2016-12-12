package kr.coffee.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.coffee.app.dto.Sale;
import kr.coffee.app.jdbc.DBCon;
import kr.coffee.app.jdbc.JdbcUtil;

public class SaleDao {
	private static final SaleDao instance = new SaleDao();
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private SaleDao(){}

	public static SaleDao getInstance() {
		return instance;
	}
	
	public int insertSale(Sale newItem){
		Connection con = DBCon.getConnection();
		String sql = "insert into sale(code, price, saleCnt, marginRate) values(?, ?, ?, ?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, newItem.getCode());
			pstmt.setInt(2, newItem.getPrice());
			pstmt.setInt(3, newItem.getSaleCnt());
			pstmt.setInt(4, newItem.getMarginRate());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}
		return -1;
	}
}
