package com.example.study.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ORDER_HISTORY")
public class OrderHistory  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_HISTORY_ID")
    private Long id;

    @Column(name = "ORDER_NAME")
    private String orderName;

    @Column(name = "PRICE")
    private Long price;

    @Column(name = "ORDER_STATUS")
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;
}
