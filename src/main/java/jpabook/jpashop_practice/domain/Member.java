package jpabook.jpashop_practice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

	@Id @GeneratedValue
	@Column(name = "member_id")
	private Long id;

	private String name;

	@Embedded
	private Address address;

	@OneToMany(mappedBy = "member") // Order 클래스(테이블) 에 있는 member 필드에 매핑되었다는 뜻(연관관계의 주인이 아니다.)
	private List<Order> orders = new ArrayList<>();
}
