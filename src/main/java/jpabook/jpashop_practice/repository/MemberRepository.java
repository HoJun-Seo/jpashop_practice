package jpabook.jpashop_practice.repository;

import jpabook.jpashop_practice.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
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
}
