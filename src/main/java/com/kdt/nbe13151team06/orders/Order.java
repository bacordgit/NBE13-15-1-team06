package com.kdt.nbe13151team06.orders;

import com.kdt.nbe13151team06.orderItems.OrderItems;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "address", nullable = false, length = 200)
    private String address;

    @Column(name = "postcode", nullable = false, length = 5)
    private String postcode;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;

    @Column(name = "shipping_date", nullable = false)
    private LocalDate shippingDate;

    @Column(name = "memo", nullable = false, length = 200)
    private String memo = "";

    @Column(name = "totalAmount", nullable = false)
    private Long totalAmount;

    @OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItems> items = new ArrayList<>();

    public Order(String email, String address, String postcode,
                 LocalDate shippingDate, LocalDateTime now) {
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.shippingDate = shippingDate;
        this.status = OrderStatus.PLACED;
        this.totalAmount = 0L;
        this.createdAt = now;
        this.updatedAt = now;

    }

    public enum OrderStatus { PLACED, CANCELLED }

    public void cancel(LocalDateTime now) {
        this.status = OrderStatus.CANCELLED;
        this.canceledAt = now;
        this.updatedAt = now;
    }

    public void changeMemo(String memo, LocalDateTime now) {
        this.memo = memo;
        this.updatedAt = now;
    }
    public void addItem(OrderItems item) {
        this.items.add(item);
        this.totalAmount = this.items.stream()
                .mapToLong(i -> i.getLine_amount())
                .sum();
    }
}



