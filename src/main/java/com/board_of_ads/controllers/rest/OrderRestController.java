package com.board_of_ads.controllers.rest;

import com.board_of_ads.models.dto.order.Order;
import com.board_of_ads.models.dto.order.OrderDto;
import com.board_of_ads.service.interfaces.OrderService;
import com.board_of_ads.service.interfaces.UserService;
import com.board_of_ads.util.Error;
import com.board_of_ads.util.ErrorResponse;
import com.board_of_ads.util.Response;
import com.board_of_ads.util.SuccessResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
@Slf4j
public class OrderRestController {

    private final OrderService orderService;

    @GetMapping("/all")
    public Response<Set<OrderDto>> getAll(Principal principal){
        Set<OrderDto> orders = orderService.getOrdersByUserEmail(principal.getName());
        if (!orders.isEmpty())
            return Response.ok(orders);
        else
            return new ErrorResponse<>(new Error(204, "No found orders"));
    }

    @GetMapping("/{id}")
    public Response<OrderDto> getById(@PathVariable Long id){
        Optional<Order> order = orderService.getOrderById(id);
        if (order.isPresent())
            return Response.ok(new OrderDto(order.get()));
        else
            return new ErrorResponse<>(new Error(204, "No order found"));
    }

    @PostMapping
    public Response<Void> addOrder(@RequestBody @Valid Order order){
        orderService.save(order);
        return new SuccessResponse<>();
    }

    @DeleteMapping("/{id}")
    public Response<Void> deleteOrder(@PathVariable Long id){
        orderService.removeById(id);
        return new SuccessResponse<>();
    }
}
