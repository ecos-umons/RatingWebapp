package uni.umons.ratingwebapp.controller;

import uni.umons.ratingwebapp.domain.dto.UserDto;
import uni.umons.ratingwebapp.security.SecurityUtil;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {

	@RequestMapping("/")
	public String hom() {
		return "redirect:/home";
	}

	@RequestMapping("/home")
	public String home() {
		UserDetails userDetails = SecurityUtil.getCurrentUser();
		if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_User"))){
			return "redirect:/user/home";
		}else if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_Administrator"))) {
			return "redirect:/user/home";
		}
		return "/401";
	}

}
