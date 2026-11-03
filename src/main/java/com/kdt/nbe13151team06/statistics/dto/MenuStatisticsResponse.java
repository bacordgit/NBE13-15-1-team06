package com.kdt.nbe13151team06.statistics.dto;

import java.time.LocalDate;

public class MenuStatisticsResponse {

    private final LocalDate date;
    private final int menuId;
    private final String menuName;
    private final int totalQuantity;
    private final long totalAmount;

    public MenuStatisticsResponse(LocalDate date, int menuId, String menuName, int totalQuantity, long totalAmount) {
        this.date = date;
        this.menuId = menuId;
        this.menuName = menuName;
        this.totalQuantity = totalQuantity;
        this.totalAmount = totalAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getMenuId() {
        return menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public long getTotalAmount() {
        return totalAmount;
    }
}
