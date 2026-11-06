package com.kdt.nbe13151team06.orders;

import com.kdt.nbe13151team06.error.ORDER_NOT_CANCELLABLE;
import com.kdt.nbe13151team06.menus.MenuRepository;
import com.kdt.nbe13151team06.menus.Menus;
import com.kdt.nbe13151team06.orderItems.OrderItems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class OrderServiceTest {
    private OrderRepository orderRepository;
    private MenuRepository menuRepository;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        this.orderRepository = mock(OrderRepository.class);
        this.menuRepository = mock(MenuRepository.class);
        this.orderService = new OrderService(this.orderRepository, this.menuRepository);
    }

    // 배송일을 정해서 접수 상태 주문 객체를 만든다
    private Order placedOrder(LocalDate shippingDate) {
        return new Order("buyer@example.com", "서울시 실습로 10 101호", "01234",
                shippingDate, LocalDateTime.of(2026, 11, 1, 10, 0, 0));
    }


    // 1. 취소
    @Test
    void cancel_before_deadline_marks_order_cancelled() {
        LocalDate tomorrow = OrderRules.now().toLocalDate().plusDays(1);
        Order order = placedOrder(tomorrow);
        given(this.orderRepository.findById(1L)).willReturn(Optional.of(order));

        OrderResponse response = this.orderService.cancel(1L);

        assertEquals("CANCELLED", response.status());
        assertEquals(Order.OrderStatus.CANCELLED, order.getStatus());
        verify(this.orderRepository).findById(1L);
    }

    //ORDER_NOT_CANCELLABLE
    @Test
    void cancel_after_deadline_throws_and_keeps_order() {
        LocalDate yesterday = OrderRules.now().toLocalDate().minusDays(1);
        Order order = placedOrder(yesterday);
        given(this.orderRepository.findById(1L)).willReturn(Optional.of(order));

        ORDER_NOT_CANCELLABLE error = assertThrows(ORDER_NOT_CANCELLABLE.class,
                () -> this.orderService.cancel(1L));

        assertEquals("배송 대상일 14시 전의 접수 상태 주문만 취소할 수 있습니다.", error.getMessage());
        assertEquals(Order.OrderStatus.PLACED, order.getStatus());
        assertNull(order.getCanceledAt());
    }


     //정확한 시간을 넣기위해 만듦
    @Test
    void deadline_rule_allows_cancel_at_13_59_59() {
        LocalDate shippingDate = LocalDate.of(2026, 11, 6);

        assertTrue(OrderRules.beforeCancelDeadline(shippingDate.atTime(13, 59, 59), shippingDate));
    }

    @Test
    void deadline_rule_rejects_cancel_at_14_00_00() {
        LocalDate shippingDate = LocalDate.of(2026, 11, 6);

        assertFalse(OrderRules.beforeCancelDeadline(shippingDate.atTime(14, 0, 0), shippingDate));
    }



    //재취소

    @Test
    void cancel_again_after_deadline_keeps_first_cancel_time() {
        LocalDate yesterday = OrderRules.now().toLocalDate().minusDays(1);
        Order order = placedOrder(yesterday);
        LocalDateTime firstCancel = yesterday.atTime(9, 0, 0);
        order.cancel(firstCancel); // 이미 취소된 주문
        given(this.orderRepository.findById(1L)).willReturn(Optional.of(order));

        OrderResponse response = this.orderService.cancel(1L);

        assertEquals("CANCELLED", response.status());
        assertEquals(firstCancel, order.getCanceledAt());
    }



     //재주문
    @Test
    void reorder_uses_current_price_and_keeps_original_order_values() {
        Menus menu = new Menus(null, "Columbia Nariño", 5000, "원두", true);
        ReflectionTestUtils.setField(menu, "id", 1L); // 재주문이 메뉴 id로 다시 조회하므로 id를 넣어 준다

        Order original = placedOrder(OrderRules.now().toLocalDate().plusDays(1));
        original.addItem(new OrderItems(original, menu, menu.getName(), 5000, 2)); // 접수 당시 단가 5000
        menu.setPrice(6000); // 이후 메뉴 가격 인상

        given(this.orderRepository.findById(1L)).willReturn(Optional.of(original));
        given(this.menuRepository.findById(1L)).willReturn(Optional.of(menu));
        given(this.orderRepository.save(any(Order.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        OrderResponse response = this.orderService.reorder(1L);

        // 새 주문: 현재 가격(6000)으로 계산
        assertEquals(6000, response.items().get(0).unitPrice());
        assertEquals(12000, response.items().get(0).lineAmount());
        assertEquals(12000L, response.totalAmount());

        // 원본 주문: 접수 당시 값 그대로
        assertEquals(5000, original.getItems().get(0).getUnit_price());
        assertEquals(10000, original.getItems().get(0).getLine_amount());
        assertEquals(10000L, original.getTotalAmount());

        verify(this.orderRepository).save(any(Order.class));
    }
}