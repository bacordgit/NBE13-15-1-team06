package com.kdt.nbe13151team06.statistics.dto;

public class DeliveryItemResponse {

    private final long menuId;
    private final String menuName;
    private final int unitPrice;
    private final int quantity;
    private final long lineAmount;

    public DeliveryItemResponse(long menuId, String menuName, int unitPrice, int quantity, long lineAmount) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.lineAmount = lineAmount;
    }

    public long getMenuId() { return menuId; }
    public String getMenuName() { return menuName; }
    public int getUnitPrice() { return unitPrice; }
    public int getQuantity() { return quantity; }
    public long getLineAmount() { return lineAmount; }
}