package com.kdt.nbe13151team06.orderItems;

import com.kdt.nbe13151team06.menus.menus;
import com.kdt.nbe13151team06.orders.Order;
import jakarta.persistence.*;

@Entity
@Table(name="orderItems")
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name="orderId",nullable=false)
    Order order;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="menuId",nullable=false)
    menus menu;

    @Column(name="menuName",nullable =false)
    String menuName;

    @Column(name="unitPrice", nullable = false)
    Integer unit_price;

    @Column(name="quantity",nullable=false)
    Integer quantity;

    @Column(name="lineAmount",nullable=false)
    Integer line_amount;
    public OrderItems(){

    }

    public OrderItems(Order order, menus menu, String menuName, Integer unit_price, Integer quantity) {
        this.order = order;
        this.menu = menu;
        this.menuName = menuName;
        this.unit_price = unit_price;
        this.quantity = quantity;
        this.line_amount=unit_price*quantity;
    }

    public Long getId() {
        return id;
    }

    public Order getOrderId() {
        return order;
    }

    public menus getMenuId() {
        return menu;
    }

    public String getMenuName() {
        return menuName;
    }

    public Integer getUnit_price() {
        return unit_price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getLine_amount() {
        return line_amount;
    }

    public void setOrderId(Order order) {
        this.order = order;
    }

    public void setMenuId(menus menu) {
        this.menu = menu;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public void setUnit_price(Integer unit_price) {
        this.unit_price = unit_price;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
