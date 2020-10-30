package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.User;
import com.board_of_ads.models.dto.order.Order;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface OrderService {
    Order save(Order order);
    Set<Order> getOrdersByUser(User user);
    Set<Order> getOrdersByOwner(User user);
    Optional<Order> getOrderById(Long id);
    List<Order> getAll();
    void deleteOrder(Order order);

    Long countOrdersByUser(User user);
    Long countOrdersByOwner(User user);
    Long countUncheckedByUser(User user);
}
