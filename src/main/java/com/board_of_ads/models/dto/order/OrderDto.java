package com.board_of_ads.models.dto.order;

import com.board_of_ads.models.dto.PostingDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private LocalDateTime date_time;
    private PostingDto posting;
    private DeliveryStatus status;

    public OrderDto(Order order){
        this.id = order.getId();
        this.date_time = order.getDateTime();
        this.posting = new PostingDto(order.getPosting());
        this.status = order.getStatus();
    }
}
