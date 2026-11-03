package com.kdt.nbe13151team06.statistics.service;

import com.kdt.nbe13151team06.error.VALIDATION_ERROR;
import com.kdt.nbe13151team06.orderItems.OrderItemRepository;
import com.kdt.nbe13151team06.orderItems.orderItems;
import com.kdt.nbe13151team06.orders.Order;
import com.kdt.nbe13151team06.orders.OrderRepository;
import com.kdt.nbe13151team06.statistics.dto.CustomerStatisticsResponse;
import com.kdt.nbe13151team06.statistics.dto.DeliveryBatchResponse;
import com.kdt.nbe13151team06.statistics.dto.DeliveryItemResponse;
import com.kdt.nbe13151team06.statistics.dto.MenuStatisticsResponse;
import com.kdt.nbe13151team06.statistics.dto.OrderStatisticsResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@Transactional(readOnly = true)
public class StatisticsService {

    private static final LocalDate MIN_DATE = LocalDate.of(2000, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(2099, 12, 31);

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public StatisticsService(OrderRepository orderRepository,
                             OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    // ============================================================
    // A17. 접수일별 주문 통계
    //  - 기준: 접수일(createdAt)이 date인 주문 전체
    // ============================================================

    public OrderStatisticsResponse getOrderStatistics(LocalDate date) {
        validateDate(date, "date");

        List<Order> orders = findOrdersOfDay(date);
        Map<Integer, List<orderItems>> itemsByOrderId = groupItemsByOrderId(orders);

        int placedOrderCount = 0;
        int cancelledOrderCount = 0;
        int totalQuantity = 0;
        long totalAmount = 0;

        for (Order order : orders) {
            if (order.getStatus() == Order.OrderStatus.PLACED) {
                placedOrderCount++;
                List<orderItems> items = itemsByOrderId.getOrDefault(order.getId(), List.of());
                totalQuantity += sumQuantity(items);
                totalAmount += sumLineAmount(items);
            } else {
                cancelledOrderCount++;
            }
        }

        return new OrderStatisticsResponse(date, orders.size(),
                placedOrderCount, cancelledOrderCount, totalQuantity, totalAmount);
    }

    // ============================================================
    // A24. 접수일별 고객 통계
    //  - 기준: 접수일(createdAt)이 date인 주문 전체
    //  - 결과: 고객(이메일)별 1건씩, 이메일 오름차순
    // ============================================================
    public List<CustomerStatisticsResponse> getCustomerStatistics(LocalDate date) {
        validateDate(date, "date");

        List<Order> orders = findOrdersOfDay(date);
        Map<Integer, List<orderItems>> itemsByOrderId = groupItemsByOrderId(orders);
        Map<String, List<Order>> ordersByEmail = groupByEmail(orders);

        List<CustomerStatisticsResponse> result = new ArrayList<>();

        for (Map.Entry<String, List<Order>> entry : ordersByEmail.entrySet()) {
            String email = entry.getKey();
            List<Order> customerOrders = entry.getValue();

            int placedOrderCount = 0;
            int cancelledOrderCount = 0;
            int totalQuantity = 0;
            long totalAmount = 0;

            for (Order order : customerOrders) {
                if (order.getStatus() == Order.OrderStatus.PLACED) {
                    placedOrderCount++;
                    List<orderItems> items = itemsByOrderId.getOrDefault(order.getId(), List.of());
                    totalQuantity += sumQuantity(items);
                    totalAmount += sumLineAmount(items);
                } else {
                    cancelledOrderCount++;
                }
            }

            result.add(new CustomerStatisticsResponse(date, email, customerOrders.size(),
                    placedOrderCount, cancelledOrderCount, totalQuantity, totalAmount));
        }

        return result;
    }

    // ============================================================
    // A18. 접수일별 메뉴 통계
    //  - 기준: 접수일(createdAt)이 date인 PLACED 주문만
    //  - 결과: 메뉴별 1건씩 (판매 수량, 판매 금액)
    //  - 정렬: 메뉴 ID → 단가 → 메뉴명
    // ============================================================

    public List<MenuStatisticsResponse> getMenuStatistics(LocalDate date) {
        validateDate(date, "date");

        List<Order> placedOrders = new ArrayList<>();
        for (Order order : findOrdersOfDay(date)) {
            if (order.getStatus() == Order.OrderStatus.PLACED) {
                placedOrders.add(order);
            }
        }

        List<orderItems> items = placedOrders.isEmpty()
                ? List.of()
                : orderItemRepository.findByOrderIdIn(placedOrders);

        List<MenuStatisticsResponse> result = new ArrayList<>();
        for (List<orderItems> sameItems : groupByMenuPriceName(items).values()) {
            orderItems first = sameItems.get(0);
            int totalQuantity = 0;
            long totalAmount = 0;
            for (orderItems item : sameItems) {
                totalQuantity += item.getQuantity();
                totalAmount += item.getLine_amount();
            }
            result.add(new MenuStatisticsResponse(first.getMenuId().getId(), first.getMenuName(),
                    first.getUnit_price(), totalQuantity, totalAmount));
        }

        result.sort(Comparator.comparingLong(MenuStatisticsResponse::getMenuId)
                .thenComparingInt(MenuStatisticsResponse::getUnitPrice)
                .thenComparing(MenuStatisticsResponse::getMenuName));
        return result;
    }

    // ============================================================
    // A14. 배송 묶음
    //  - 기준: 접수일이 아니라 배송일(shippingDate)이 같은 PLACED 주문
    //  - 결과: 고객(이메일)별 배송 묶음 1건씩, 이메일 오름차순
    //  - 한 묶음 = 주소·우편번호 + orderIds(오름차순) + 합산된 items
    //  - items는 A18과 같은 규칙으로 합산 (ID → 단가 → 메뉴명 순)
    // ============================================================

    public List<DeliveryBatchResponse> getDeliveryBatches(LocalDate shippingDate) {
        validateDate(shippingDate, "shippingDate");

        List<Order> orders = orderRepository
                .findByShippingDateAndStatusOrderByEmailAsc(shippingDate, Order.OrderStatus.PLACED);
        Map<Integer, List<orderItems>> itemsByOrderId = groupItemsByOrderId(orders);
        Map<String, List<Order>> ordersByEmail = groupByEmail(orders);

        List<DeliveryBatchResponse> result = new ArrayList<>();

        for (Map.Entry<String, List<Order>> entry : ordersByEmail.entrySet()) {
            String email = entry.getKey();
            List<Order> customerOrders = entry.getValue();
            Order first = customerOrders.get(0);

            List<Integer> orderIds = new ArrayList<>();
            List<orderItems> batchItems = new ArrayList<>();

            for (Order order : customerOrders) {
                orderIds.add(order.getId());
                batchItems.addAll(itemsByOrderId.getOrDefault(order.getId(), List.of()));
            }
            orderIds.sort(null);

            List<DeliveryItemResponse> deliveryItems = mergeDeliveryItems(batchItems);
            int totalQuantity = 0;
            long totalAmount = 0;
            for (DeliveryItemResponse item : deliveryItems) {
                totalQuantity += item.getQuantity();
                totalAmount += item.getLineAmount();
            }

            result.add(new DeliveryBatchResponse(
                    shippingDate, email, first.getAddress(), first.getPostcode(),
                    orderIds, orderIds.size(), totalQuantity, totalAmount, deliveryItems));
        }

        return result;
    }


    // -------------------

// 날짜 검사
    private void validateDate(LocalDate date, String fieldName) {
        int year = date.getYear();
        if (year < 2000 || year > 2099) {
            throw new VALIDATION_ERROR();
        }
    }
// 하루에 들어온 주문 전체 조회

    private List<Order> findOrdersOfDay(LocalDate date) {
        return orderRepository.findByCreatedAtGreaterThanEqualAndCreatedAtLessThan(
                date.atStartOfDay(), date.plusDays(1).atStartOfDay());
    }


 // 이메일 묶기
    private Map<String, List<Order>> groupByEmail(List<Order> orders) {
        Map<String, List<Order>> map = new TreeMap<>();
        for (Order order : orders) {
            map.computeIfAbsent(order.getEmail(), k -> new ArrayList<>()).add(order);
        }
        return map;
    }

    // 주문 번호 묶기
    private Map<Integer, List<orderItems>> groupItemsByOrderId(List<Order> orders) {
        Map<Integer, List<orderItems>> map = new LinkedHashMap<>();
        if (orders.isEmpty()) {
            return map;
        }
        for (orderItems item : orderItemRepository.findByOrderIdIn(orders)) {
            map.computeIfAbsent(item.getOrderId().getId(), k -> new ArrayList<>()).add(item);
        }
        return map;
    }

    // 같은 메뉴 묶기
    private Map<String, List<orderItems>> groupByMenuPriceName(List<orderItems> items) {
        Map<String, List<orderItems>> map = new LinkedHashMap<>();
        for (orderItems item : items) {
            String key = item.getMenuId().getId() + "|" + item.getUnit_price() + "|" + item.getMenuName();
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(item);
        }
        return map;
    }

    // 주문 수량 합계
    private int sumQuantity(List<orderItems> items) {
        int sum = 0;
        for (orderItems item : items) {
            sum += item.getQuantity();
        }
        return sum;
    }

    private long sumLineAmount(List<orderItems> items) {
        long sum = 0;
        for (orderItems item : items) {
            sum += item.getLine_amount();
        }
        return sum;
    }

    private List<DeliveryItemResponse> mergeDeliveryItems(List<orderItems> items) {
        List<DeliveryItemResponse> result = new ArrayList<>();
        for (List<orderItems> sameItems : groupByMenuPriceName(items).values()) {
            orderItems first = sameItems.get(0);
            int quantity = 0;
            long lineAmount = 0;
            for (orderItems item : sameItems) {
                quantity += item.getQuantity();
                lineAmount += item.getLine_amount();
            }
            result.add(new DeliveryItemResponse(first.getMenuId().getId(), first.getMenuName(),
                    first.getUnit_price(), quantity, lineAmount));
        }

        result.sort(Comparator.comparingLong(DeliveryItemResponse::getMenuId)
                .thenComparingInt(DeliveryItemResponse::getUnitPrice)
                .thenComparing(DeliveryItemResponse::getMenuName));
        return result;
    }
}