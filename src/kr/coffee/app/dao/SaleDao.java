package kr.coffee.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kr.coffee.app.dto.Product;
import kr.coffee.app.dto.Sale;
import kr.coffee.app.dto.SaleDetail;
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
			pstmt.setString(1, newItem.getProduct().getCode());
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
	
	public List<Sale> selectSaleDetailOrderBySalePrice(boolean isSalePrice){
		Connection con = DBCon.getConnection();
		StringBuilder sql = new StringBuilder();
		if (isSalePrice){
			sql.append("select (select count(*)+1 from sale_detail s2 where s2.sale_price > s1.sale_price) rank, ");
		}else{
			sql.append("select (select count(*)+1 from sale_detail s2 where s2.marginPrice > s1.marginPrice) rank, ");
		}
		sql.append("s1.code code,"
				+ "p.name name, price , salecnt , supply_price , addTax , sale_price , marginRate , marginPrice "
				+ "from sale inner join sale_detail s1 on sale.code = s1.code join product p on s1.code = p.code order by rank");
		
		List<Sale> list = new ArrayList<Sale>();
		try {
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while (rs.next()){
				list.add(getSale(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return list;
	}
	
	private Sale getSale(ResultSet rs) throws SQLException {
		Product product = ProductDao.getInstance().selectProductNameByPcode(rs.getString("code"));
		int price = rs.getInt("price");
		int saleCnt = rs.getInt("salecnt");
		int marginRate=rs.getInt("marginRate");
		SaleDetail saleDetail = new SaleDetail(rs.getString("code"), rs.getInt("sale_Price"), rs.getInt("addTax"), 
				rs.getInt("supply_price"), rs.getInt("marginPrice"), rs.getInt("rank"));
		return new Sale(product, price, saleCnt, marginRate, saleDetail);
	}
	
	public String[] getTotal(){
		Connection con = DBCon.getConnection();
		String sql = "select sum(supply_price) , sum(addTax) ,sum(sale_price),  sum(marginPrice) from sale_detail";
		String[] total = new String[10];
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			DecimalFormat df = new DecimalFormat("#,###");
			if (rs.next()){
				total[0]="합계";
				Arrays.fill(total, 1, 4, "");
				total[5]=df.format(rs.getInt(1));
				total[6]=df.format(rs.getInt(2));
				total[7]=df.format(rs.getInt(3));
				total[8]="";
				total[9]=df.format(rs.getInt(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return total;
	}
}
