package kr.coffee.app.dto;

public class SaleDetail {

	private String code;
	private int sale_Price;
	private int addTax;
	private int supply_price;
	private int marginPrice;
	private int rank;
	
	public SaleDetail(String code) {
		this.code = code;
	}

	public SaleDetail(String code, int sale_Price, int addTax, int supply_price, int marginPrice) {
		this.code = code;
		this.sale_Price = sale_Price;
		this.addTax = addTax;
		this.supply_price = supply_price;
		this.marginPrice = marginPrice;
	}
	
	public SaleDetail(String code, int sale_Price, int addTax, int supply_price, int marginPrice, int rank) {
		this.code = code;
		this.sale_Price = sale_Price;
		this.addTax = addTax;
		this.supply_price = supply_price;
		this.marginPrice = marginPrice;
		this.rank = rank;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getSale_Price() {
		return sale_Price;
	}

	public void setSale_Price(int sale_Price) {
		this.sale_Price = sale_Price;
	}

	public int getAddTax() {
		return addTax;
	}

	public void setAddTax(int addTax) {
		this.addTax = addTax;
	}

	public int getSupply_price() {
		return supply_price;
	}

	public void setSupply_price(int supply_price) {
		this.supply_price = supply_price;
	}

	public int getMarginPrice() {
		return marginPrice;
	}

	public void setMarginPrice(int marginPrice) {
		this.marginPrice = marginPrice;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
	
}
