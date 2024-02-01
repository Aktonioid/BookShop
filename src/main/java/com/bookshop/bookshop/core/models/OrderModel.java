package com.bookshop.bookshop.core.models;

import java.sql.Date;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel 
{
    @Id
    private UUID id;
    
    @Column(name = "is_send")
    private boolean isSend; // отправлен ли заказ

    @OneToMany
    @JoinColumn(name = "order_id")
    private Set<OrderPartModel> books;

    @Temporal(TemporalType.DATE)
    @Basic
    @Column(name = "send_date")
    private Date sendDate;

    @Column(name = "user_full_name")
    private String userFullName;

    @Column(name = "delivery_adress")
    private String deliveryAdress;

    @Column(name = "payment_status")
    private boolean paymentStatus; // хз в каком формате оставлю. Мб тут оставлю для типа связи со складом
                                  // но проверку на то оплачен заказ или нет через другую табличку, которая работает с типа платежной системой

    
}
