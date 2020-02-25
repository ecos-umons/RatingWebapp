package uni.umons.ratingwebapp.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uni.umons.ratingwebapp.domain.GitUser;

public interface GituserRepository extends BaseDao<GitUser, Long> {

	GitUser findGitUsersByName(String username);
	GitUser findByGitUserId(Long Id);
}