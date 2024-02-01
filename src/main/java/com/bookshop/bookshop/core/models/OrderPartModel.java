package com.bookshop.bookshop.core.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_part")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderPartModel 
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;    
    
    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookModel book;

    @Column(name = "book_count")
    private int bookCount;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderModel orderId;


}
