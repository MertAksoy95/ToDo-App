package com.mertaksoy.todoapp.repository;

import com.mertaksoy.todoapp.entity.User;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CouchbaseRepository<User, String> {

    User findByUsername(String email);

}
