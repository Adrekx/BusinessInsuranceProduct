package com.networkinsurance.BIP.helper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CriteriaHelper {

    @PersistenceContext
    private EntityManager entityManager;

    public <T>  void buildTheCriteriaQuery(Class<T> rootClass, Map<SimpleEntry<Integer, String>, String> filterMap, List<T> results){
        // Initialize CriteriaBuilder and CriteriaQuery
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(rootClass);
        Root<T> root = query.from(rootClass);
        results.addAll(addAndPredicates(cb, query, root, filterMap));

    }

    public <T> List<T> addAndPredicates(CriteriaBuilder cb, CriteriaQuery<T> query, Root<T> root, Map<SimpleEntry<Integer,String>, String> filtersMap){
        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<SimpleEntry<Integer,String>, String> filter : filtersMap.entrySet()) {
            SimpleEntry<Integer, String> entry = filter.getKey();
            Integer conditionType = entry.getKey(); // If conditionType == 0 => It's direct field of the table else it's FK relationship
            String field = entry.getValue();
            String value = filter.getValue();

            if (value != null) {
                if(conditionType == 0) {
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
