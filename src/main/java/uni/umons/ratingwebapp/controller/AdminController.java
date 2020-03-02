
package uni.umons.ratingwebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uni.umons.ratingwebapp.domain.dto.RateDto;
import uni.umons.ratingwebapp.service.DataServices;

import javax.annotation.PreDestroy;
import javax.annotation.security.RolesAllowed;
import java.util.List;


@RequestMapping(value = "/admin/")
@Controller
public class AdminController {

	@Autowired
	private DataServices dataServices;

	@PreAuthorize("hasRole('Administrator')")
	@RequestMapping(value = "dashboard")
	public String dashboard() {
		return "dashboard";
	}

	@PreAuthorize("hasRole('Administrator')")
	@RequestMapping(value = "home")
	public String home() {
		return "admin/home";
	}

	@PreAuthorize("hasRole('Administrator')")
	@RequestMapping(value = "allrateditems")
	public String AnalyzedItems() {
		return "admin/analyzed";
	}

	@PreAuthorize("hasRole('Administrator')")
	@RequestMapping(value = "edititems")
	public String editItems() {
		return "admin/edititems";
	}

	@PreAuthorize("hasRole('Administrator')")
	@RequestMapping(value = "agreement")
	public String results() {
		return "admin/results";
	}



	@PreAuthorize("hasRole('User') or hasRole('Administrator')")
	@RequestMapping(value = "getAllRates" , method = RequestMethod.POST
			,produces = "application/json")
	public @ResponseBody
	List<RateDto> GivemeAllRates()
	{
		return dataServices.getAllRates();
	}
}
