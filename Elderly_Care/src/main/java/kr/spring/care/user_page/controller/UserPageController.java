package kr.spring.care.user_page.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.care.user.entity.User;
import kr.spring.care.user_page.dto.UserDTO;
import kr.spring.care.user_page.service.UserPageService;
import lombok.RequiredArgsConstructor;

@RequestMapping("/userPage/*")
@Controller
@RequiredArgsConstructor
public class UserPageController {
	
	private final UserPageService userPageService;
	
	@GetMapping("myinfo/{id}")
	public String myinfo(@PathVariable("id") long userId, Model model) {
		model.addAttribute("myInfo", userPageService.myInfo(userId));
		return "userPage/myinfo";
	}
	
	@PutMapping("edit")
	@ResponseBody
	public String edit(@RequestBody UserDTO userDTO) {
	    User user = userPageService.findUserByEmail(userDTO.getEmail());
	    if (user != null) {
	        // 기본 사용자 정보 업데이트 로직 구현
	        // 예: user.setName(userEditDTO.getName());

	        // hasGuardian 처리
	        if (user.getSenior() != null) {
	            user.getSenior().setHasGuardian(userDTO.getHasGuardian());
	            // Senior 엔티티 업데이트 로직 구현
	        }

	        userPageService.editUser(user); // 수정된 User 엔티티 저장
	    }
	    return user.getEmail(); // 수정 후 응답
	}

	
//	@PutMapping("edit")
//	@ResponseBody
//	public String edit(@RequestBody User user) {
//		userPageService.editUser(user);
//		return user.getEmail();
//	}
	
	@PutMapping("editPw")
	@ResponseBody
	public String editPw(@RequestBody User user) {
		userPageService.editPw(user);
		return "success";
	}
	
	
	@GetMapping("matchingInfo")
	public String matchingInfo() {
		return "userPage/matchingInfo";
	}
	
	
}
