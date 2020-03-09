package uni.umons.ratingwebapp.domain;



import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_rate")
@NamedNativeQueries(
		{       @NamedNativeQuery(name = "Rate.getStats",
				resultSetMapping = "myMapping1",
				query = "SELECT rater_id as userId, COUNT(*) AS totalRate, COUNT(IF(rate='1',1,null)) As humanRate, COUNT(IF(rate='2',1,null)) AS botRate, " +
						"COUNT(IF(rate='3',1,null)) AS idkRate, COUNT(IF(rate_difficulty='1',1,null)) AS veryEasyRate, COUNT(IF(rate_difficulty='2',1,null)) AS easyRate, " +
						"COUNT(IF(rate_difficulty='3',1,null)) AS difficultRate, COUNT(IF(rate_difficulty='4',1,null)) AS veryDifficultRate, " +
						"COUNT(IF(rate_difficulty='0',1,null)) AS noDifficulty FROM tbl_rate WHERE tbl_rate.rate != 0 GROUP BY rater_id"),
				@NamedNativeQuery(name = "Rate.getBotStats",
						resultSetMapping = "myMapping2",
						query = "SELECT gituser as userId, COUNT(*) AS totalRate, COUNT(IF(rate='1',1,null)) As humanRate, " +
								"COUNT(IF(rate='2',1,null)) AS botRate, COUNT(IF(rate='3',1,null)) AS idkRate FROM tbl_rate " +
								"WHERE tbl_rate.rate != 0 GROUP BY gituser;"),
				@NamedNativeQuery(name = "Rate.getUserTimely",
						resultSetMapping = "myMapping3",
						query = "SELECT rater_id as userId, COUNT(*) AS total, DATE_FORMAT(rated_at, \"%Y-%m-%d\") AS ratedAt " +
								"FROM tbl_rate  WHERE rated_at is not NULL And rated_at > (NOW() - INTERVAL 1 MONTH)" +
								"GROUP BY rater_id, DATE_FORMAT(rated_at, \"%Y-%m-%d\") ORDER BY DATE_FORMAT(rated_at, \"%Y-%m-%d\");")

		}
)
@SqlResultSetMapping(
		name="myMapping1",
		classes={
				@ConstructorResult(
						targetClass=Stats.class,
						columns={
								@ColumnResult(name="userId"),
								@ColumnResult(name="totalRate"),
								@ColumnResult(name="humanRate"),
								@ColumnResult(name="botRate"),
								@ColumnResult(name="idkRate"),
								@ColumnResult(name="veryEasyRate"),
								@ColumnResult(name="easyRate"),
								@ColumnResult(name="difficultRate"),
								@ColumnResult(name="veryDifficultRate"),
								@ColumnResult(name="noDifficulty")
						}
				)
		}
)
@SqlResultSetMapping(
		name="myMapping2",
		classes={
				@ConstructorResult(
						targetClass= BotStats.class,
						columns={
								@ColumnResult(name="userId"),
								@ColumnResult(name="totalRate"),
								@ColumnResult(name="humanRate"),
								@ColumnResult(name="botRate"),
								@ColumnResult(name="idkRate")
						}
				)
		}
)
@SqlResultSetMapping(
		name="myMapping3",
		classes={
				@ConstructorResult(
						targetClass= TimeSeriesStats.class,
						columns={
								@ColumnResult(name="userId"),
								@ColumnResult(name="total"),
								@ColumnResult(name="ratedAt")
						}
				)
		}
)
public class Rate  implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long rateId;

	@Basic(optional = false)
	@NotNull
	@Column(name = "gituser")
	private Long gitUserId;

	@JsonBackReference
	@JoinColumn(name = "gituser", referencedColumnName = "id", insertable = false, updatable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private GitUser gitUser;

	@Column(name = "rated_at")
	private LocalDateTime ratedAt;

	@Basic(optional = false)
	@NotNull
	@Column(name = "rater_id")
	private Long raterid;

	@JsonBackReference
	@JoinColumn(name = "rater_id", referencedColumnName = "id", insertable = false, updatable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private User rater;

	@Column(name = "rate")
	private Short rate;

	@Column(name = "rate_difficulty")
	private Short rateDifficulty;

	@Column(name = "description",length = 255)
	private String description;

	public Long getRateId() {
		return rateId;
	}

	public void setRateId(Long rateId) {
		this.rateId = rateId;
	}

	public Long getGitUserId() {
		return gitUserId;
	}

	public void setGitUserId(Long gitUserId) {
		this.gitUserId = gitUserId;
	}

	public GitUser getGitUser() {
		return gitUser;
	}

	public void setGitUser(GitUser gitUser) {
		this.gitUser = gitUser;
	}

	public LocalDateTime getRatedAt() {
		return ratedAt;
	}

	public void setRatedAt(LocalDateTime ratedAt) {
		this.ratedAt = ratedAt;
	}

	public Long getRaterid() {
		return raterid;
	}

	public void setRaterid(Long raterid) {
		this.raterid = raterid;
	}

	public User getRater() {
		return rater;
	}

	public void setRater(User rater) {
		this.rater = rater;
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
}
