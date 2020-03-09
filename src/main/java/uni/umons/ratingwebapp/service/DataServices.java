package uni.umons.ratingwebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uni.umons.ratingwebapp.domain.*;
import uni.umons.ratingwebapp.domain.dto.*;
import uni.umons.ratingwebapp.repository.*;
import uni.umons.ratingwebapp.security.SecurityUtil;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("services")
public class DataServices {

	private Logger logger = LogManager.getLogger(DataServices.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private RateRepository rateRepository;

	@Autowired
	private GituserRepository gituserRepository;

	@Transactional
	public User getCurrentUserDetail(){
		return userRepository.findByUsername(SecurityUtil.getCurrentUser().getUsername());
	}

	@Transactional
	public Rate getLastIncompleteRate(){
		return rateRepository.findRateByRaterAndRate(getCurrentUserDetail(),Short.parseShort("0"));
	}

	@Transactional
	public GitUserDto getNextGituser() {
		User user = getCurrentUserDetail();
		GitUser nextGitUser = null;
		Rate rate = rateRepository.findRateByRaterAndRate(user,Short.parseShort("0"));
		if(rate != null){
			nextGitUser = gituserRepository.findByGitUserId(rate.getGitUserId());
		}else {
			nextGitUser = gituserRepository.findNextUser(user.getUserId());
			rate = new Rate();
			rate.setGitUserId(nextGitUser.getGitUserId());
			rate.setRaterid(user.getUserId());
			rate.setRate(Short.parseShort("0"));
			rateRepository.save(rate);
		}
		logger.info("Next GitUser retrieved.");
		return EntityMappers.GitUsertoGitUserDto(nextGitUser);
	}

	@Transactional
	public GitUserDto getGituser(Long id) {
		GitUser gituser = gituserRepository.findByGitUserId(id);
		logger.info("GitUser "+ gituser.getName() +" retrieved.");
		return EntityMappers.GitUsertoGitUserDto(gituser);
	}

	@Transactional
	public void RecordNewRate(RateDto ratedto)
	{
		logger.info("New rate added");
		Rate rate = ratedto.toEntity();
		rate.setRaterid(userRepository.findByUsername(ratedto.getRaterName()).getUserId());
		rateRepository.save(rate);
	}

	@Transactional
	public void UpdateRate(Rate rate)
	{
		logger.info("Rate updated.");
		rateRepository.save(rate);
	}

	@Transactional
	public GitUser getGitUser(String user,String repo) {
		logger.info("Authority group list is requested");
		return gituserRepository.findGitUsersByName(user);
	}

	@Transactional
	public List<RateDto> getUserRates(){
		logger.info("load user rates");
		return EntityMappers.RatesToRatesDto(rateRepository.findAllByRater(userRepository.findByUsername(SecurityUtil.getCurrentUser().getUsername())));
	}

	@Transactional
	public List<RateDto> getAllRates(){
		return EntityMappers.RatesToRatesDto(rateRepository.findAll());
	}

	private Stats findstat(Long id,List<Stats> stats)
	{
		for(Stats stat : stats)
		{
			if(stat.getUserId().equals(Integer.parseInt(id.toString())))
			{
				return stat;
			}
		}
		return null;
	}

	private String getName(Integer id,List<User> users){
		for(User user : users){
			if(user.getUserId().equals(Long.parseLong(id.toString())))
			{
				return user.getFirstName() + " " + user.getLastName();
			}
		}
		return "";
	}

	@Transactional
	public HomePageStats getRatesStats(){
		HomePageStats homeStats = new HomePageStats();

		List<User> users = userRepository.findAll();
		List<Stats> stats = rateRepository.getStats();
		List<BotStats> botStats = rateRepository.getBotStats();
		List<TimeSeriesStats> lastMonthStats = rateRepository.getUserTimely();

		List<UserStatisticDto> userStat = new ArrayList<>();
		for(int i=0;i<users.size();i++)
		{
			UserStatisticDto userStatistic = new UserStatisticDto();
			userStatistic.setName(users.get(i).getFirstName()+ " " +users.get(i).getLastName());
			userStatistic.setStats(findstat(users.get(i).getUserId(),stats));
			userStat.add(userStatistic);
		}

		for(int i=0;i<lastMonthStats.size();i++)
		{
			lastMonthStats.get(i).setName(getName(lastMonthStats.get(i).getUserId(),users));
		}


		homeStats.setUserStats(userStat);
		homeStats.setBotStats(botStats);
		homeStats.setLastMonthStats(lastMonthStats);

		return homeStats ;
	}

	@Transactional
	public List<RatedGitUserDto> getAllRatedAccounts(){
		return EntityMappers.GitusersToRatedGitUserDto(gituserRepository.getAllRatedUsers());
	}
}
