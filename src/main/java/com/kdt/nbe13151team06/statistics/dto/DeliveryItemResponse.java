package com.kdt.nbe13151team06.statistics.dto;

public class DeliveryItemResponse {

    private final int menuId;
    private final String menuName;
    private final int unitPrice;
    private final int quantity;
    private final long lineAmount;

    public DeliveryItemResponse(int menuId, String menuName, int unitPrice, int quantity, long lineAmount) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.lineAmount = lineAmount;
    }

    public int getMenuId() {
        return menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public long getLineAmount() {
        return lineAmount;
    }
}
