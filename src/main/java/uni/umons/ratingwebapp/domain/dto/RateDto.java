package uni.umons.ratingwebapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import uni.umons.ratingwebapp.domain.Rate;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class RateDto implements BaseDto<uni.umons.ratingwebapp.domain.Rate> {

	private Long rateId;

	private Long gitUser;

	private String gitUserName;

	private String gitUserRepo;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime ratedAt;

	private Long raterId;

	private String raterName;

	private Short rate;

	private Short rateDifficulty;

	private String description;

	public Long getRateId() {
		return rateId;
	}

	public void setRateId(Long rateId) {
		this.rateId = rateId;
	}

	public LocalDateTime getRatedAt() {
		return ratedAt;
	}

	public void setRatedAt(LocalDateTime ratedAt) {
		this.ratedAt = ratedAt;
	}

	public Short getRate() {
		return rate;
	}

	public void setRate(Short rate) {
		this.rate = rate;
	}

	public Short getRateDifficulty() {
		return rateDifficulty;
	}

	public void setRateDifficulty(Short rateDifficulty) {
		this.rateDifficulty = rateDifficulty;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getGitUser() {
		return gitUser;
	}

	public void setGitUser(Long gitUser) {
		this.gitUser = gitUser;
	}

	public String getRaterName() {
		return raterName;
	}

	public void setRaterName(String raterName) {
		this.raterName = raterName;
	}

	public Long getRaterId() {
		return raterId;
	}

	public void setRaterId(Long raterId) {
		this.raterId = raterId;
	}

	public String getGitUserName() {
		return gitUserName;
	}

	public void setGitUserName(String gitUserName) {
		this.gitUserName = gitUserName;
	}

	public String getGitUserRepo() {
		return gitUserRepo;
	}

	public void setGitUserRepo(String gitUserRepo) {
		this.gitUserRepo = gitUserRepo;
	}

	@Override
	public uni.umons.ratingwebapp.domain.Rate toEntity() {
		Rate rate = new Rate();
		rate.setRate(this.rate);
		rate.setGitUserId(this.getGitUser());
		rate.setDescription(this.getDescription());
		rate.setRatedAt(this.getRatedAt());
		rate.setRateDifficulty(this.getRateDifficulty());
		return rate;
	}
}
