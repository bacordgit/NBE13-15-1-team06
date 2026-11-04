package com.kdt.nbe13151team06.orders;

import com.kdt.nbe13151team06.error.ADDRESS_MISMATCH;
import com.kdt.nbe13151team06.error.MenuNotFoundException;
import com.kdt.nbe13151team06.error.ORDER_NOT_FOUND;
import com.kdt.nbe13151team06.error.ValidationException;
import com.kdt.nbe13151team06.exception.MENU_UNAVAILABLE;
import com.kdt.nbe13151team06.exception.ORDER_NOT_CANCELLABLE;
import com.kdt.nbe13151team06.menus.menuRepository;
import com.kdt.nbe13151team06.menus.menus;
import com.kdt.nbe13151team06.orderItems.OrderItemResponse;
import com.kdt.nbe13151team06.orderItems.orderItems;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final menuRepository menuRepo;

    public OrderService(OrderRepository orderRepository, menuRepository menuRepo) {
        this.orderRepository = orderRepository;
        this.menuRepo = menuRepo;
    }

    // A11 주문 접수
    @Transactional
    public OrderResponse create(OrderCreateRequest request) {
        LocalDateTime now = OrderRules.now();
        LocalDate shippingDate = OrderRules.shippingDateFor(now);
        String address = request.address().strip();
        String postcode = request.postcode();
        List<OrderItemRequest> items = request.items();

        // 메뉴 중복 검증
        for (int i = 0; i < items.size(); i++) {
            for (int j = i + 1; j < items.size(); j++) {
                if (items.get(i).menuId().equals(items.get(j).menuId())) {
                    throw new ValidationException();
                }
            }
        }

        // 메뉴 존재 검증
        List<menus> found = new ArrayList<>();
        for (OrderItemRequest item : items) {
            menus menu = this.menuRepo.findById(item.menuId())
                    .orElseThrow(() -> new MenuNotFoundException());
            found.add(menu);
        }

        // 판매 중지 메뉴여부
        for (menus menu : found) {
            if (!menu.isAvailable()) {
                throw new MENU_UNAVAILABLE("");
            }
        }

        // 같은 이메일 + 배송일의 접수 주문과 배송지가 같은여부
        List<Order> sameDay = this.orderRepository.findByEmailAndShippingDateAndStatus(
                request.email(), shippingDate, Order.OrderStatus.PLACED);
        for (Order other : sameDay) {
            if (!other.getAddress().strip().equals(address)
                    || !other.getPostcode().equals(postcode)) {
                throw new ADDRESS_MISMATCH();
            }
        }

        // 주문, 항목 저장
        Order order = new Order(request.email(), address, postcode, shippingDate, now);

        for (int i = 0; i < items.size(); i++) {
            menus menu = found.get(i);
            order.addItem(new orderItems(order, menu, menu.getName(), menu.getPrice(),items.get(i).quantity()));
        }
        Order saved = this.orderRepository.save(order);
        return toResponse(saved);
    }

    // A12 이메일별 목록
    @Transactional(readOnly = true)
    public List<OrderListResponse> listByEmail(String email) {
        List<OrderListResponse> result = new ArrayList<>();
        for (Order order : this.orderRepository.findByEmailOrderByCreatedAtDescIdDesc(email)) {
            result.add(toListResponse(order));
        }
        return result;
    }

    // A13 주문상세
    @Transactional(readOnly = true)
    public OrderResponse get(Long id) {
        return toResponse(find(id));
    }

    // A16 주문 취소
    @Transactional
    public OrderResponse cancel(Long id) {
        Order order = find(id);
        if (order.getStatus() == Order.OrderStatus.CANCELLED) {
            return toResponse(order); // 재취소는 취소상태 유지
        }
        LocalDateTime now = OrderRules.now();
        if (!OrderRules.beforeCancelDeadline(now, order.getShippingDate())) {// 14시이전 판단
            throw new ORDER_NOT_CANCELLABLE("");
        }
        order.cancel(now);
        return toResponse(order);
    }

    // A22 주문 메모 수정
    @Transactional
    public OrderResponse updateMemo(Long id, OrderUpdateRequest request) {
        Order order = find(id);
        order.changeMemo(request.memo(), OrderRules.now());
        return toResponse(order);
    }

    // A23 재주문
    @Transactional
    public OrderResponse reorder(Long id) {
        Order old = find(id);
        List<OrderItemRequest> items = new ArrayList<>();
        for (orderItems item : old.getItems()) {
            items.add(new OrderItemRequest(item.getMenuId().getId(), item.getQuantity()));
        }
        return create(new OrderCreateRequest(old.getEmail(), old.getAddress(),
                old.getPostcode(), items));
    }

    private Order find(Long id) {
        return this.orderRepository.findById(id)
                .orElseThrow(() -> new ORDER_NOT_FOUND(""));
    }

    //order 엔티티를 orderResponse 로 보낼 dto로 바꾸는 과정임
    private OrderResponse toResponse(Order o) {
        List<OrderItemResponse> items = new ArrayList<>();
        for (orderItems item : o.getItems()) {
            items.add(toItemResponse(item));
        }
        return new OrderResponse(
                o.getId(),
                o.getEmail(),
                o.getAddress(),
                o.getPostcode(),
                OrderTimeFormat.format(o.getCreatedAt()),
                o.getShippingDate().toString(),
                o.getTotalAmount(),
                items,
                o.getStatus().name(),
                OrderTimeFormat.format(o.getCanceledAt()),
                o.getMemo());
    }

    // order 엔티티를 orderListResponse 로 보낼 dto로 바꾸는 과정
    private OrderListResponse toListResponse(Order o) {
        return new OrderListResponse(
                o.getId(),
                OrderTimeFormat.format(o.getCreatedAt()),
                o.getShippingDate().toString(),
                o.getTotalAmount(),
                o.getStatus().name());
    }

    // orderItems 엔티티를 orderItemResponse 로 보낼 dto로 바꾸는 과정
    private OrderItemResponse toItemResponse(orderItems i) {
        return new OrderItemResponse(
                i.getMenuId().getId(),
                i.getMenuName(),
                i.getUnit_price(),
                i.getQuantity(),
                i.getLine_amount());
    }
}