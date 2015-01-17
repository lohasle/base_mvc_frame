package com.lohasle.baseframe.s4m3.pojo;

/**
 * 房屋价格变动pojo
 * @author ganmin
 */
public class RoomPrice {
	private Integer roomId;
	private float area;
	private float unitPrice;
	private float totalPrice;
	
	public RoomPrice(){
	}
	
	public RoomPrice(int roomId, float area, float unitPrice, float totalPrice) {
		this.roomId = roomId;
		this.area = area;
		this.unitPrice = unitPrice;
		this.totalPrice = totalPrice;
	}

	public Integer getRoomId() {
		return this.roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public float getArea() {
		return area;
	}

	public void setArea(float area) {
		this.area = area;
	}

	public float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
}
