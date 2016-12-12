package kr.coffee.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.coffee.app.dto.SaleInfo;
import kr.coffee.app.jdbc.DBCon;
import kr.coffee.app.jdbc.JdbcUtil;

public class SaleInfoDao {
	private static final SaleInfoDao instance = new SaleInfoDao();
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private SaleInfoDao(){}

	public static SaleInfoDao getInstance() {
		return instance;
	}
	
	public ArrayList<SaleInfo> selectAllbyOrderBySalePrice(){
		Connection con = DBCon.getConnection();
		String sql = "select (select count(*)+1 from v_output where salePrice > p1.salePrice) as rank, "
				+ "code, name, price, saleCnt, supplyPrice, addTax, salePrice, marginRate, marginPrice "
				+ "from v_output p1 order by rank";
		ArrayList<SaleInfo> lists = new ArrayList<SaleInfo>();
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				lists.add(getSaleInfo(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return lists;
	}
	
	private SaleInfo getSaleInfo(ResultSet rs) throws SQLException {
		int rank = rs.getInt("rank");
		String code = rs.getString("code");
		String name = rs.getString("name");
		int price = rs.getInt("price");
		int saleCnt = rs.getInt("saleCnt");
		int supplyPrice = rs.getInt("supplyPrice");
		int addTax = rs.getInt("addTax");
		int salePrice = rs.getInt("salePrice");
		int marginRate = rs.getInt("marginRate");
		int marginPrice = rs.getInt("marginPrice");
		return new SaleInfo(rank, code, name, price, saleCnt, supplyPrice, addTax, salePrice, marginRate, marginPrice);
	}

	public ArrayList<SaleInfo> selectAllbyOrderByMarginPrice(){
		Connection con = DBCon.getConnection();
		String sql = "select (select count(*)+1 from v_output where marginPrice > p1.marginPrice) as rank, "
				+ "code, name, price, saleCnt, supplyPrice, addTax, salePrice, marginRate, marginPrice "
				+ "from v_output p1 order by rank;";
		ArrayList<SaleInfo> lists = new ArrayList<SaleInfo>();
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				lists.add(getSaleInfo(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return lists;
	}
	
	public SaleInfo selectTotal(){
		Connection con = DBCon.getConnection();
		String sql = "select sum(supplyPrice), sum(addTax), sum(salePrice), sum(marginPrice) from v_output";
		SaleInfo saleInfo = null;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()){
				saleInfo = new SaleInfo();
				saleInfo.setSupplyPrice(rs.getInt(1));
				saleInfo.setAddTax(rs.getInt(2));
				saleInfo.setSalePrice(rs.getInt(3));
				saleInfo.setMarginPrice(rs.getInt(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return saleInfo;
	}
}
