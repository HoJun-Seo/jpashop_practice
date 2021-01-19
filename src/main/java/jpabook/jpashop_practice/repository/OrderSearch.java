package jpabook.jpashop_practice.repository;

import jpabook.jpashop_practice.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {

	private String memberName;
	private OrderStatus orderStatus;
}