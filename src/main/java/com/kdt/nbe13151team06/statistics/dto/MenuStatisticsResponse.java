package com.kdt.nbe13151team06.statistics.dto;

public class MenuStatisticsResponse {

    private final long menuId;
    private final String menuName;
    private final int unitPrice;
    private final int totalQuantity;
    private final long totalAmount;

    public MenuStatisticsResponse(long menuId, String menuName, int unitPrice,
                                  int totalQuantity, long totalAmount) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.unitPrice = unitPrice;
        this.totalQuantity = totalQuantity;
        this.totalAmount = totalAmount;
    }

    public long getMenuId() { return menuId; }
    public String getMenuName() { return menuName; }
    public int getUnitPrice() { return unitPrice; }
    public int getTotalQuantity() { return totalQuantity; }
    public long getTotalAmount() { return totalAmount; }
}