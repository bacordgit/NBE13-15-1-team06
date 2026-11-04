package com.kdt.nbe13151team06.orders;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // A11 주문 접수 - 201
    @PostMapping
    public ResponseEntity<OrderResponse> create(
            @Valid @RequestBody OrderCreateRequest request) {
        OrderResponse response = this.orderService.create(request);
        URI location = URI.create("/api/v1/orders/" + response.id());

        return ResponseEntity.created(location).body(response);
    }

    // A12 이메일별 주문 - 200
    @GetMapping
    public List<OrderListResponse> list(@RequestParam("email") String email) {
        return this.orderService.listByEmail(email);
    }

    // A13 주문 상세 - 200
    @GetMapping("/{orderId}")
    public OrderResponse get(@PathVariable("orderId") Long orderId) {
        return this.orderService.get(orderId);
    }

    // A16 주문 취소
    @PostMapping("/{orderId}/cancel")
    public OrderResponse cancel(@PathVariable("orderId") Long orderId) {
        return this.orderService.cancel(orderId);
    }

    // A22 주문 메모 수정
    @PutMapping("/{orderId}/memo")
    public OrderResponse updateMemo(@PathVariable("orderId") Long orderId,
                                    @Valid @RequestBody OrderUpdateRequest request) {
        return this.orderService.updateMemo(orderId, request);
    }

    // A23 재주문
    @PostMapping("/{orderId}/reorder")
    public ResponseEntity<OrderResponse> reorder(@PathVariable("orderId") Long orderId) {
        OrderResponse response = this.orderService.reorder(orderId);
        URI location = URI.create("/api/v1/orders/" + response.id());
        return ResponseEntity.created(location).body(response);
    }
}