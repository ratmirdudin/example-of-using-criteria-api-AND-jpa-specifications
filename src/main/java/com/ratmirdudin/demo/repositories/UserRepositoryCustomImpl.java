package com.ratmirdudin.demo.repositories;

import com.ratmirdudin.demo.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    private final EntityManager em;

    @Override
    public int updateInfoAboutUser(Long id, String firstName, String lastName) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<User> criteriaUpdate = cb.createCriteriaUpdate(User.class);
        Root<User> userRoot = criteriaUpdate.from(User.class);
        if (firstName != null) {
            criteriaUpdate.set("firstName", firstName);
        }
        if (lastName != null) {
            criteriaUpdate.set("lastName", lastName);
        }
        criteriaUpdate.where(cb.equal(userRoot.get("id"), id));
        Query query = em.createQuery(criteriaUpdate);
        return query.executeUpdate();
    }
}
