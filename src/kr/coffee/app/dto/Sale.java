package kr.coffee.app.dto;

import java.text.DecimalFormat;

public class Sale {
	private Product product;
	private int price;
	private int saleCnt;
	private int marginRate;
	private SaleDetail saleDetail;
	
	public Sale() {}

	public Sale(Product product, int price, int saleCnt, int marginRate) {
		this.product = product;
		this.price = price;
		this.saleCnt = saleCnt;
		this.marginRate = marginRate;
	}

	public Sale(Product product, int price, int saleCnt, int marginRate, SaleDetail saleDetail) {
		this.product = product;
		this.price = price;
		this.saleCnt = saleCnt;
		this.marginRate = marginRate;
		this.saleDetail = saleDetail;
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

	public SaleDetail getSaleDetail() {
		return saleDetail;
	}

	public void setSaleDetail(SaleDetail saleDetail) {
		this.saleDetail = saleDetail;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "Sale [product=" + product + ", price=" + price + ", saleCnt=" + saleCnt + ", marginRate=" + marginRate
				+ ", saleDetail=" + saleDetail + "]";
	}
	
	//	순위,	제품코드,	제품명,	제품단가,	판매수량,	공급가액,	부가세액,	판매금액,	마진율,	마진액

	public String[] toArray(){
		DecimalFormat df = new DecimalFormat("#,###");
		return new String[]{saleDetail.getRank()+"", product.getCode(), product.getName(), df.format(price), df.format(saleCnt), 
				df.format(saleDetail.getSupply_price()), df.format(saleDetail.getAddTax()), df.format(saleDetail.getSale_Price()), String.format("%d%%", marginRate), df.format(saleDetail.getMarginPrice())};
	}
}








