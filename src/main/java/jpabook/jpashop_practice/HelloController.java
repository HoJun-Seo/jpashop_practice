package jpabook.jpashop_practice;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

	@GetMapping("hello")
	public String hello(Model model){
		// Model : 스프링 ui 에 있는 Model 에 데이터를 실어서 뷰로 넘겨줄 수 있다.
		model.addAttribute("data", "hello!!!");
		return "hello"; // 화면 이름
	}
}
