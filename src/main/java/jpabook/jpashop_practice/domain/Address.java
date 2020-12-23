package jpabook.jpashop_practice.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
// 값 타입은 기본적으로 값 자체가 immutable 하게, 즉 변경이 되게 하면 안된다.
// 그래서 값 타입을 사용할 때 가장 좋은 설계 방법은 처음 값을 생성할 때 만 값이 세팅되고
// Setter 를 제공하지 않는 것이다.(생성 이후 변경 불가능 - 값을 변경하려면 아예 새로 값을 만들어야 한다.)
public class Address {

	private String city;
	private String street;
	private String zipcode;

	public Address(String city, String street, String zipcode) {
		this.city = city;
		this.street = street;
		this.zipcode = zipcode;
	}
	/*
	그런데 JPA 에서 기본 스펙상 JPA 에서 객체와 같은 값을 생성할 땐
	리플렉션이나 프록시 같은 기술을 사용해야 하는데, 기본 생성자(default constructor) 가 없으면
	위와 같은 기능들을 활용할 수 없기 때문에 반드시 기본 생성자를 함께 만들어 주어야 한다.
	 */

	//public Address() {
	//}

	/*
	그런데 여기서 public 으로 기본 생성자를 만들면 사람들이 이걸 많이 호출하게 될 수도 있다.
	그렇기 때문에 JPA 에서는 기본 생성자를 protected 로 생성하는 것 까지 허가해주고 있다.
	 */

	protected Address(){

	}
}
