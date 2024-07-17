package com.mertaksoy.todoapp.repository;

import com.mertaksoy.todoapp.entity.Category;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CouchbaseRepository<Category, String> {

    Category findByTitleAndUserId(String title, String userId);

    Page<Category> findAllByUserId(Pageable pageable, String userId);

}
