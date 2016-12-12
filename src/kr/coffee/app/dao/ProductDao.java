package kr.coffee.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.coffee.app.dto.Product;
import kr.coffee.app.jdbc.DBCon;
import kr.coffee.app.jdbc.JdbcUtil;

public class ProductDao {
	private static final ProductDao instance = new ProductDao();
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private ProductDao(){}
	
	public static ProductDao getInstance() {
		return instance;
	}

	public Product selectProductNameByPcode(String code){
		Connection con = DBCon.getConnection();
		String sql = "select code, name from product where code=?";
		Product product = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, code);
			rs = pstmt.executeQuery();
			if (rs.next()){
				product = new Product(rs.getString("code"), rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return product;
	}
}
