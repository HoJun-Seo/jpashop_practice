package jpabook.jpashop_practice.repository;

import jpabook.jpashop_practice.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository // Spring Bean 으로 등록한다.(어노테이션을 까보면 @Component 어노테이션으로 컴포넌트의 대상임을 지정하고 있다.)
public class MemberRepository {

	@PersistenceContext
	private EntityManager em;

	public void save(Member member){
		em.persist(member);
	}

	public Member findOne(Long id){
		return em.find(Member.class, id);
	}

	public List<Member> findAll(){
		/*
		List<Member> result = em.createQuery("select m from Member m", Member.class)
				.getResultList();

		return result
		 */

		// ctrl + alt + n 키를 통해 위의 코드들을 한 줄의 리턴문으로 합쳐줄 수 있다.

		return em.createQuery("select m from Member m", Member.class)
				.getResultList();
	}

	// 만약 이름으로 멤버를 검색할 경우
	public List<Member> findByName(String name){
		return em.createQuery("select m from Member m where m.name = :name", Member.class)
				.setParameter("name", name)
				.getResultList();
	}
}
