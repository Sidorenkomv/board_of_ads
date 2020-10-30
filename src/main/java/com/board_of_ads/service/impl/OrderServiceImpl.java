package com.board_of_ads.service.impl;

import com.board_of_ads.models.User;
import com.board_of_ads.models.dto.order.Order;
import com.board_of_ads.repository.OrderRepository;
import com.board_of_ads.repository.UserRepository;
import com.board_of_ads.service.interfaces.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        orderRepository.save(order);
        return order;
    }

    @Override
    public Set<Order> getOrdersByUser(User user) {
        return orderRepository.findAllByUser(user);
    }

    @Override
    public Set<Order> getOrdersByOwner(User user){
        return orderRepository.findAllByPostingUser(user);
    }

    @Override
    public List<Order> getAll(){
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }

    @Override
    public Long countOrdersByUser(User user) {
        return orderRepository.countOrdersByUser(user);
    }

    @Override
    public Long countUncheckedByUser(User user){
        return orderRepository.countByCheckedFalseAndUserIs(user);
    }

    @Override
    public Long countOrdersByOwner(User user) {
        return orderRepository.countOrdersByPostingUser(user);
    }
}
