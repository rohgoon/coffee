package kr.coffee.app.dto;

public class Sale {
	private String code;
	private int price;
	private int saleCnt;
	private int marginRate;
	
	public Sale() {}

	public Sale(String code, int price, int saleCnt, int marginRate) {
		this.code = code;
		this.price = price;
		this.saleCnt = saleCnt;
		this.marginRate = marginRate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getSaleCnt() {
		return saleCnt;
	}

	public void setSaleCnt(int saleCnt) {
		this.saleCnt = saleCnt;
	}

	public int getMarginRate() {
		return marginRate;
	}

	public void setMarginRate(int marginRate) {
		this.marginRate = marginRate;
	}

	@Override
	public String toString() {
		return String.format("Sale [%s %s %s %s]", code,	price, saleCnt, marginRate);
	}
	
}
