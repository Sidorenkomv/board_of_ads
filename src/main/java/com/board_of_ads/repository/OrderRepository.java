package com.board_of_ads.repository;

import com.board_of_ads.models.dto.order.Order;
import com.board_of_ads.models.User;
import com.board_of_ads.models.dto.order.OrderDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Set<Order> findAllByUser(User user);
    Set<Order> findAllByPostingUser(User user);

    @Query("select new com.board_of_ads.models.dto.order.OrderDto(o) from Order o where o.user.email = :email")
    Set<OrderDto> findAllByUser_EmailDto(String email);

    Long countOrdersByUser(User user);
    Long countOrdersByPostingUser(User user);

    Long countByCheckedFalseAndUserIs(User user);

    void removeById(Long id);
}
