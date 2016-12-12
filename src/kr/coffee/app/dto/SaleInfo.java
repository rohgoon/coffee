package kr.coffee.app.dto;

import java.text.DecimalFormat;

public class SaleInfo {
	private int rank;
	private String code;	//제품코드
	private String name;	//제품명
	private int price;		//제품단가
	private int saleCnt;	//판매수량
	private int supplyPrice;//공급가액 (판매금액-부가세액)
	private int addTax;		//부가세액 (판매금액/11 소수첫자리에서 올림)
	private int salePrice;	//판매금액 (제품단가*판매수량)
	private int marginRate;	//마진율
	private int marginPrice;//마진액	(공급가액*마진율 소수첫자리에서 반올림)
	
	
	public SaleInfo() {	}

	public SaleInfo(String code, String name, int price, int saleCnt, int marginRate) {
		this.code = code;
		this.name = name;
		this.price = price;
		this.saleCnt = saleCnt;
		this.marginRate = marginRate;
		
		this.salePrice = this.price * this.saleCnt;
		this.addTax = (int) Math.ceil(this.salePrice/11.0);
		this.supplyPrice = this.salePrice - this.addTax;
		this.marginPrice = (int) Math.round(this.supplyPrice* (this.marginRate/100.0));
	}

	public SaleInfo(int rank, String code, String name, int price, int saleCnt, int supplyPrice, int addTax,
			int salePrice, int marginRate, int marginPrice) {
		this.rank = rank;
		this.code = code;
		this.name = name;
		this.price = price;
		this.saleCnt = saleCnt;
		this.supplyPrice = supplyPrice;
		this.addTax = addTax;
		this.salePrice = salePrice;
		this.marginRate = marginRate;
		this.marginPrice = marginPrice;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public int getSaleCnt() {
		return saleCnt;
	}

	public int getMarginRate() {
		return marginRate;
	}

	public int getSalePrice() {
		return salePrice;
	}

	public int getAddTax() {
		return addTax;
	}

	public int getSupplyPrice() {
		return supplyPrice;
	}

	public int getMarginPrice() {
		return marginPrice;
	}

	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddTax(int addTax) {
		this.addTax = addTax;
	}

	public void setSupplyPrice(int supplyPrice) {
		this.supplyPrice = supplyPrice;
	}

	public void setMarginPrice(int marginPrice) {
		this.marginPrice = marginPrice;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setSaleCnt(int saleCnt) {
		this.saleCnt = saleCnt;
	}

	public void setMarginRate(int marginRate) {
		this.marginRate = marginRate;
	}

	@Override
	public String toString() {
		return String
				.format("SaleInfo [%s %s %s %s %s %s %s %s %s %s]",
						rank, code, name, price, saleCnt, marginRate, salePrice,
						addTax, supplyPrice, marginPrice);
	}
	
	public String[] toArray(){
		DecimalFormat df = new DecimalFormat("#,###");
		String[] saleInfo={(price==0)?"합계":String.valueOf(rank), code, name, (price==0)?"":df.format(price), (saleCnt==0)?"":String.valueOf(saleCnt), df.format(supplyPrice), df.format(addTax), df.format(salePrice), (marginRate==0)?"":String.valueOf(marginRate)+"%", df.format(marginPrice)};
		return saleInfo; 
	}
}
