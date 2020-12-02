package jpabook.jpashop_practice;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

	@PersistenceContext // 영속성 컨텍스트 어노테이션(JPA를 사용하기 때문에 EntityManager 가 필요하다.)
	private EntityManager em;
	// 스프링 부트에서 위와 같은 어노테이션이 있으면, EntityManager 를 주입해준다.

	public Long save(Member member){ // Entity 객체를 저장하기 위한 메소드
		em.persist(member);
		return member.getId(); // 왜 키 값을 반환해야 할까?
		// 커맨드와 쿼리를 분리하라?(CQRS?) -> 당장은 알 필요 없는것 같다.
		// 저장을 하고 나면 사이드 이펙트를 일으킬 수 있는 커맨드 성 이기 때문에
		// 리턴 값을 거의 만들지 않는다.
		// 대신 아이디 값을 받아올 수 있으면 다음에 다시 조회 정도는 할 수 있다.
	}

	public Member find(Long id){
		return em.find(Member.class, id);
	}
}
