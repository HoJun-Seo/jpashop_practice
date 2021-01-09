package jpabook.jpashop_practice.repository;

import jpabook.jpashop_practice.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

	private final EntityManager em;

	public void save(Item item){
		if (item.getId() == null){
			em.persist(item); // Item 객체는 처음 저장할땐 id 값이 없다.
			// id 값이 없다면 완전히 새로 호출한 객체라는 뜻
		}
		else{
			em.merge(item); //update 비슷한거, 자세한건 뒤에서 설명
			// 웹 애플리케이션에서 아예 차트를 하나 만들었는데, 그때 설명함

			// id값이 있다면 이미 DB 에 존재하고 있는걸 가져왔다는 뜻이기 때문에
			// merger 메소드를 통해 데이터를 합병(merge) 함으로서 update 한다고 생각하면 될 듯 하다.
		}
	}

	public Item findOne(Long id){
		return em.find(Item.class, id);
	}

	public List<Item> findAll(){
		return em.createQuery("select i from Item i", Item.class)
				.getResultList();
	}
}
