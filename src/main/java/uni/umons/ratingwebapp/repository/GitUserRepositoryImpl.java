package uni.umons.ratingwebapp.repository;

import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import uni.umons.ratingwebapp.domain.GitUser;

import javax.persistence.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import uni.umons.ratingwebapp.domain.Rate;
import uni.umons.ratingwebapp.domain.Stats;

import java.util.List;

@Repository
@Qualifier(value = "gituserRepository")
public class GitUserRepositoryImpl extends BaseDaoImpl<GitUser, Long> implements GituserRepository {

    private Logger logger = LogManager.getLogger(GitUserRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    public GitUserRepositoryImpl() {
        super(GitUser.class);
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public GitUser findGitUsersByName(String username) {
        try {
            TypedQuery<GitUser> userTypedQuery = entityManager.createNamedQuery("GitUser.findByName", GitUser.class);
            userTypedQuery.setParameter("name", username);

            return userTypedQuery.getSingleResult();

        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public GitUser findByGitUserId(Long Id) {
        try {
            TypedQuery<GitUser> userTypedQuery = entityManager.createNamedQuery("GitUser.findById", GitUser.class);
            userTypedQuery.setParameter("id", Id);

            return userTypedQuery.getSingleResult();

        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public GitUser findNextUser(Long uid) {
        try {
            Long nextAccount = Long.parseLong(entityManager
                    .createNativeQuery(
                            "SELECT id FROM tbl_github_user WHERE id NOT IN (SELECT gituser FROM tbl_rate GROUP BY gituser HAVING COUNT(*) > 1 UNION ALL SELECT gituser FROM tbl_rate WHERE rater_id = :uid) ORDER BY id LIMIT 1"
                    )
                    .setParameter("uid", uid)
                    .getSingleResult().toString());

            return findByGitUserId(nextAccount);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public List<GitUser> getAllRatedUsers() {
        try {
            return  entityManager
                    .createNativeQuery(
                            "SELECT * FROM tbl_github_user WHERE id in (select gituser from tbl_rate WHERE rate != 0) ;", GitUser.class
                    )
                    .getResultList();
        } catch (Exception ex) {
            throw ex;
        }
    }

//    @Override
//    public List<Stats> getStat() {
//        try {
//             = entityManager.createNativeQuery("GitUser.getStats",GitUser.class);
////            TypedQuery<GitUser> userTypedQuery = entityManager.createNamedQuery("GitUser.getStats");
//
//            return userTypedQuery.getSingleResult();
//
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }
}