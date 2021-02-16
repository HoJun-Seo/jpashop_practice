package jpabook.jpashop_practice.controller;

import jpabook.jpashop_practice.domain.Address;
import jpabook.jpashop_practice.domain.Member;
import jpabook.jpashop_practice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/members/new")
	public String createForm(Model model){
		// 컨트롤러 -> 뷰로 넘어갈 때 데이터를 model 에 실어서 넘긴다.
		model.addAttribute("memberForm", new MemberForm());
		return "/members/createMemberForm";
		/*
		컨트롤러를 통해 화면을 이동할 때 MemberForm 이라는 빈 껍데기 객체를 가져간다.
		그에 따라 화면에서 MemberForm 객체에 접근할 수 있게된다.
		 */
	}

	@PostMapping("/members/new") // url 경로는 똑같으나, get 은 Form 화면을 열어보고, post 는 데이터를 실제로 등록해주는 역할을 한다.
	public String create(@Valid MemberForm form, BindingResult result){ // MemberForm 객체가 파라미터로 넘어오게 된다.
		// 여기서 파라미터로 넘어오는 객체에 유효성 검증을 해줄수 있다.(Validation)
		// @Valid 어노테이션을 활용해주면 Form 에 있는 Spring 이 javax.validation 기능을 쓰는 것을 인지하게 된다.
		// 그로인해 Form 에 있는 @NotEmpty 와 같은 유효성 검증 코드를 통해 Spring 에서 validation 기능을 제공해주게 된다.
		if (result.hasErrors()){
			return "members/createMemberForm"; // 에러가 있을 경우 해당 경로로 리턴
			// 스프링이 BindingResult 를 화면까지 다 끌고 가준다.(Spring-thymeleaf 간 integrition(통합) 이 라이브러리를 통해 그만큼 잘 되어져 있다.)
		}

		Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

		Member member = new Member();
		member.setName(form.getName());
		member.setAddress(address);

		memberService.join(member);
		return "redirect:/"; // 데이터 등록 절차를 마치고 나면 페이지 경로를 홈 페이지로 돌려준다.

	}

	@GetMapping("/members")
	public String list(Model model){
		List<Member> members = memberService.findMembers();
		model.addAttribute("members", members);
		return "members/membersList";
	}
}
