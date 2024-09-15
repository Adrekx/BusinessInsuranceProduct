package com.networkinsurance.BIP.helper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CriteriaHelper {

    @PersistenceContext
    private EntityManager entityManager;

    public <T>  void buildTheCriteriaQuery(Class<T> rootClass, Map<String, String> filterMap, List<T> results){
        // Initialize CriteriaBuilder and CriteriaQuery
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(rootClass);
        Root<T> root = query.from(rootClass);
        results.addAll(addAndPredicates(cb, query, root, filterMap));

    }

    public <T> List<T> addAndPredicates(CriteriaBuilder cb, CriteriaQuery<T> query, Root<T> root, Map<String, String> filtersMap){
        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String, String> filter : filtersMap.entrySet()) {
            String field = filter.getKey();
            String value = filter.getValue();

            if (value != null) {
                if(field.equals("name")) {
                    predicates.add(cb.like(cb.lower(root.get(field)), "%" + value.toLowerCase() + "%"));
                }
                else {
                    predicates.add(cb.like(cb.lower(root.get(field).get("name")), "%" + value.toLowerCase() + "%"));
                }
            }

        }
        query.where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query).getResultList();
    }

}
