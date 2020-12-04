package jpabook.jpashop_practice;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // Junit 에게 스프링으로 테스트 한다고 알려주기 위한 어노테이션
@SpringBootTest
public class MemberRepositoryTest {

	@Autowired MemberRepository memberRepository;

	@Test
	@Transactional
	@Rollback(false)
	public void testMember() throws Exception{
	    //given
		Member member = new Member();
		member.setUsername("memberA");
		//when
		Long saveId = memberRepository.save(member); // ctrl + alt + v 단축키 활용(코드에서 원하는 변수를 뽑아 올 수 있다.)
		Member findmember = memberRepository.find(saveId);
		//then
		// 검증에서 Assertions 는 assertj 라는 라이브러리를 스프링 테스트가 자동으로 가지고 있다.
		Assertions.assertThat(findmember.getId()).isEqualTo(member.getId());
		Assertions.assertThat(findmember.getUsername()).isEqualTo(member.getUsername());
		// 그런데 위와 같이 코드를 작성한 후 테스트를 수행하면 에러가 발생하게 된다.
		// No EntityManager with actual transaction available for current thread
		// 트랜잭션이 없다는 뜻이다. EntityManager 를 통한 모든 데이터 변경은 항상 트랜잭션 내부에서 수행되어야 한다.
		// 그렇기 때문에 메소드에서 @Transactional 어노테이션을 선언하여 트랜잭션을 만들어 준 후 메소드를 실행해야 한다.
		// 테스트 가 성공했을 때 H2 콘솔을 통해 데이터베이스 상태를 보면 Member 테이블이 생성되어 있는 것을 확인할 수 있다.
		// 그런데 데이터베이스에 데이터가 들어가 있지 않다. 왜 일까?
		// 그 이유는 바로 일반적으로 test 가 아닌곳에 Transactional 어노테이션이 있으면 정상적으로 트랜잭션이 수행되서 데이터가 저장되나,
		// Test 에 있는 경우 데이터베이스를 롤백해 버림으로서 데이터가 모두 날아가기 때문이다.
		// 데이터베이스를 롤백하는 이유는, 데이터가 그대로 남아있으면 반복적인 테스트를 하기 힘들기 때문이다.
		// 그런데 여기서 @Rollback(false) 로 어노테이션을 하나 더 선언해두면, 데이터베이스가 롤백되지 않고 데이터를 그대로 남겨두고 있는 것을 확인할 수 있다.

		Assertions.assertThat(findmember).isEqualTo(member);
		System.out.println("findmember == member : " + (findmember == member));
		// 위의 코드의 결과는 어떻게 나올까? (findmember 와 member 의 == 비교)
		// 들을 == 비교하면 True 가 나온다
		// 왜? 한 트랜잭션에서 같은 영속성 컨텍스트에 있을 경우 아이디 값이 같다면 같은 Entity 로 보기 때문이다.
		// 생성된 Hibernate SQL 을 잘 보면 select 절 조차 나오지 않은 것을 알 수 있다.

	}
}