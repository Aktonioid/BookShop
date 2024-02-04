package com.bookshop.bookshop.dtos;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderModelDto 
{
    private UUID id;
    
    private boolean isSend;
    private List<OrderPartModelDto> books;
    private Date sendDate;
    private String userFullName;
    private String deliveryAdress;
    private boolean paymentStatus;

    public OrderModelDto(UUID id)
    {
        this.id = id;
    }
}
