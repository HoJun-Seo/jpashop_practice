package jpabook.jpashop_practice.domain;

import jpabook.jpashop_practice.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

	@Id @GeneratedValue
	@Column(name = "order_item_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	private int orderPrice; // 주문 가격
	private int count; // 주문 수량

	// 생성 메소드
	public static OrderItem createOrderItem(Item item, int orderPrice, int count){
		OrderItem orderItem = new OrderItem();
		orderItem.setItem(item);
		orderItem.setOrderPrice(orderPrice);
		orderItem.setCount(count);

		item.removeStock(count); //주문한 수량 만큼 재고에서 차감
		return orderItem;
		/*
		 주문이 들어오면 우선적으로 이 생성 메소드에서 주문 상품에 대한 정보들을 세팅해준 이후(orderItem),
		 주문 수량에 맞춰서 현재 상품 재고 수량을 차감시킨뒤, Order 도메인 클래스에 있는 createOrder 메소드에
		 주문 상품에 대한 데이터를 넘겨준다.
		 */

	}


	// 비즈니스 로직
	public void cancel() {
		getItem().addStock(count); // 상품이 취소 되었으므로 재고를 다시 주문수량 만큼 늘려준다.
	}

	// 조회 로직
	/*
	주문 상품 전체 가격조회
	 */
	public int getTotalPrice() {
		return getOrderPrice() * getCount();
	}
}
