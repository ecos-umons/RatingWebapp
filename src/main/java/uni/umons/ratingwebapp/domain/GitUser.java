package uni.umons.ratingwebapp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tbl_github_user")
@NamedQueries({
		@NamedQuery(name = "GitUser.findAll", query = "SELECT u FROM GitUser u"),
		@NamedQuery(name = "GitUser.findById", query = "SELECT u FROM GitUser u WHERE u.gitUserId = :id"),
		@NamedQuery(name = "GitUser.findByName", query = "SELECT u FROM GitUser u WHERE u.name = :name"),
		@NamedQuery(name = "GitUser.getNextUser", query = "SELECT u FROM GitUser u WHERE u.id > :id ORDER BY id"),
})
public class GitUser  implements Serializable {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	private Long gitUserId;
	
	@Column(name = "user_login", length = 100)
	private String name;

	@Column(name = "repo")
	private String repository;

	@Column(name = "comments_in_repo")
	private Long commentInRepo;

	@Column(name = "repository_count")
	private Long repositoryCount;

	@Column(name = "all_comments")
	private Long allComments;

	@Column(name="bot_in_name")
	private Boolean botInName;

	@Column(name="fast_response")
	private Long fastResponse;

	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "gitUser")
	private List<Comment> comments;

	@JsonManagedReference
	@OneToMany(mappedBy = "gitUser", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	private List<Rate> rates;

	public Long getGitUserId() {
		return gitUserId;
	}

	public void setGitUserId(Long gitUserId) {
		this.gitUserId = gitUserId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}

	public Long getCommentInRepo() {
		return commentInRepo;
	}

	public void setCommentInRepo(Long commentInRepo) {
		this.commentInRepo = commentInRepo;
	}

	public Long getRepositoryCount() {
		return repositoryCount;
	}

	public void setRepositoryCount(Long repositoryCount) {
		this.repositoryCount = repositoryCount;
	}

	public Long getAllComments() {
		return allComments;
	}

	public void setAllComments(Long allComments) {
		this.allComments = allComments;
	}

	public Boolean getBotInName() {
		return botInName;
	}

	public void setBotInName(Boolean botInName) {
		this.botInName = botInName;
	}

	public Long getFastResponse() {
		return fastResponse;
	}

	public void setFastResponse(Long fastResponse) {
		this.fastResponse = fastResponse;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Rate> getRates() {
		return rates;
	}

	public void setRates(List<Rate> rates) {
		this.rates = rates;
	}
}
