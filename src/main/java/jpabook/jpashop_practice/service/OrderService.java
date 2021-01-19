package jpabook.jpashop_practice.service;

import jpabook.jpashop_practice.domain.Delivery;
import jpabook.jpashop_practice.domain.Member;
import jpabook.jpashop_practice.domain.Order;
import jpabook.jpashop_practice.domain.OrderItem;
import jpabook.jpashop_practice.domain.item.Item;
import jpabook.jpashop_practice.repository.ItemRepository;
import jpabook.jpashop_practice.repository.MemberRepository;
import jpabook.jpashop_practice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;

	// 주문
	@Transactional
	public Long order(Long memberId, Long ItemId, int count){
		Member member = memberRepository.findOne(memberId);
		Item item = itemRepository.findOne(ItemId);
		
		// 배송정보 생성
		Delivery delivery = new Delivery();
		delivery.setAddress(member.getAddress()); // 실제로는 배송지 정보를 직접 입력하는 편이 좋으나 예제 간편화를 위해 멤버 데이터의 주소 정보를 그대로 가져온다.

		// 주문상품 생성
		OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

		// 주문 생성
		Order order = Order.createOrder(member, delivery, orderItem);

		// 주문 저장
		orderRepository.save(order); // cascade 옵션으로 인해 내부에 연관관계 메핑된 Entity 데이터들까지 함께 persist 된다.
		return order.getId();
	}

	// 취소
	@Transactional
	public void cancelOrder(Long orderId){ // 주문을 취소할 땐 주문에 대한 id값만 받아온다.
		// 주문 엔티티 조회
		Order order = orderRepository.findOne(orderId);
		// 주문 취소
		order.cancel();
	}

	// 검색
	/*
	public List<Order> findOrders(OrderSearch orderSerach){
		return orderRepository.findAll(orderSerach);
	}
	 */
}