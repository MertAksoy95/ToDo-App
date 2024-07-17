package com.mertaksoy.todoapp.repository;

import com.mertaksoy.todoapp.entity.Task;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CouchbaseRepository<Task, String> {

    Page<Task> findAllByCategoryId(Pageable pageable, String categoryId);

}
