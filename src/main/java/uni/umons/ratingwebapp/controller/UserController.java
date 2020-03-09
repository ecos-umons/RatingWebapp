package uni.umons.ratingwebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uni.umons.ratingwebapp.domain.GitUser;
import uni.umons.ratingwebapp.domain.Rate;
import uni.umons.ratingwebapp.domain.User;
import uni.umons.ratingwebapp.domain.dto.GitUserDto;
import uni.umons.ratingwebapp.domain.dto.HomePageStats;
import uni.umons.ratingwebapp.domain.dto.RateDto;
import uni.umons.ratingwebapp.domain.dto.UserStatisticDto;
import uni.umons.ratingwebapp.security.SecurityUtil;
import uni.umons.ratingwebapp.service.DataServices;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;


@RequestMapping(value = "/user/")
@Controller
public class UserController {

	@Autowired
	private DataServices dataServices;

	@PreAuthorize("hasRole('User') or hasRole('Administrator')")
	@RequestMapping(value = "home" , method = RequestMethod.GET)
	public String home() {

		return "user/home";
	}

	@PreAuthorize("hasRole('User') or hasRole('Administrator')")
	@RequestMapping(value = "ratenext" , method = RequestMethod.GET)
	public String rate() {
		
		return "user/rate";
	}

	@PreAuthorize("hasRole('User') or hasRole('Administrator')")
	@RequestMapping(value = "rateditems",method = RequestMethod.GET)
	public String rateditems() {

		return "user/rateditems";
	}

	@PreAuthorize("hasRole('User') or hasRole('Administrator')")
	@RequestMapping(value = "description",method = RequestMethod.GET)
	public String description() {
		return "user/description";
	}

	@PreAuthorize("hasRole('User') or hasRole('Administrator')")
	@RequestMapping(value = "givemeNextGituser" , method = RequestMethod.POST
			,produces = "application/json")
	public @ResponseBody
	GitUserDto GivemeNextGituser()
	{
		return dataServices.getNextGituser();
	}


	@PreAuthorize("hasRole('User') or hasRole('Administrator')")
	@RequestMapping(value = "/submitrate" ,method = RequestMethod.POST)
	public @ResponseBody String submitrate(@RequestParam Long gitUserId,
										   @RequestParam Short rate,
										   @RequestParam Short rateDifficulty,
										   @RequestParam String rateDescription, HttpServletResponse response) {
		try {
			Rate raterecord = dataServices.getLastIncompleteRate();
			if(! gitUserId.equals(raterecord.getGitUserId()))
			{
				response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
				return "User has modified data manually";
			}
			raterecord.setRate(rate);
			raterecord.setRateDifficulty(rateDifficulty);
			raterecord.setDescription(rateDescription);
			raterecord.setRatedAt(LocalDateTime.now());
			dataServices.UpdateRate(raterecord);
			response.setStatus(HttpServletResponse.SC_OK);
			return "OK";
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return "NOK,"+e.getMessage();
		}
	}

	@PreAuthorize("hasRole('User') or hasRole('Administrator')")
	@RequestMapping(value = "getUserRates" , method = RequestMethod.POST
			,produces = "application/json")
	public @ResponseBody
	List<RateDto> GivemeUserRates()
	{
		return dataServices.getUserRates();
	}


	@PreAuthorize("hasRole('User') or hasRole('Administrator')")
	@RequestMapping(value = "getStats" , method = RequestMethod.POST
			,produces = "application/json")
	public @ResponseBody
	HomePageStats GetStats()
	{
		return dataServices.getRatesStats();
	}
}
