package jpabook.jpashop_practice.service;

import jpabook.jpashop_practice.domain.Member;
import jpabook.jpashop_practice.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;
	
	@Test
	@Rollback(value = false)
	public void 회원가입() throws Exception{
	    //given
		Member member = new Member();
		member.setName("kim");
	    //when
		Long saveId = memberService.join(member);

		//then
		Assert.assertEquals(member, memberRepository.findOne(saveId));
	}
	
	@Test(expected = IllegalStateException.class) // @Test 어노테이션에 다음과 같은 옵션을 부여하면 try - catch 문을 사용할 필요가 없다.(코드가 간결해짐)
	public void 중복_회원_예외() throws Exception{
	    //given
	    Member member1 = new Member();
	    member1.setName("kim1");

		Member member2 = new Member();
		member2.setName("kim1");
	    //when
		memberService.join(member1);
		memberService.join(member2);
		/*
		try{
			memberService.join(member2); // 예외가 발생해야 한다.
		} catch (IllegalStateException e){
			return; // 해당 익셉션이 발생할 경우 프로그램 종료
		}
		 */

		/*
		join 메소드 내부에서 유효성 검증을 위해 생성한 validateDuplicateMember
		메소드가 있는데, 이 메소드 내부에서 이름이 같은 회원이 있을 경우 IllegalStateException 을
		발생시키도록 해놨기 때문에 이름이 같은 회원이 있는것을 감지하면 위의 익셉션이 터져야 한다.
		 */
	    
	    //then
		Assert.fail("예외가 발생해야 한다.");
		/*
		Assert 클래스의 fail 메소드 : 파라미터가 null 값이 아니면 AssertionError 를 발생시킨다.
		*/
	}
}