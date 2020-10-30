package com.board_of_ads.repository;

import com.board_of_ads.models.dto.order.Order;
import com.board_of_ads.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Set<Order> findAllByUser(User user);
    Set<Order> findAllByPostingUser(User user);

    Long countOrdersByUser(User user);
    Long countOrdersByPostingUser(User user);

    Long countByCheckedFalseAndUserIs(User user);
}
