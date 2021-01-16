package jpabook.jpashop_practice.domain.item;

import jpabook.jpashop_practice.domain.Category;
import jpabook.jpashop_practice.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public abstract class Item { // 구현체를 가질 것이기 때문에 추상 클래스로 생성한다.

	@Id @GeneratedValue
	@Column(name = "item_id")
	private Long id;

	private String name;
	private int price;
	private int stockQuantity;

	@ManyToMany(mappedBy = "items")
	private List<Category> categories = new ArrayList<>();

	// 비즈니스 로직 추가(재고 늘리고 줄이기)
	
	// 재고 증가
	public void addStock(int quantity){
		this.stockQuantity += quantity;
	}
	
	// 재고 감소
	public void removeStock(int quantity){
		int restStock = this.stockQuantity - quantity;
		if (restStock < 0) { // 0 보다 적어지면 안되기에 유효성 검증 코드를 넣는다.
			throw new NotEnoughStockException("need more stock");
		}
		this.stockQuantity = restStock;
	}

}
