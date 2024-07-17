package com.mertaksoy.todoapp.config;


import com.mertaksoy.todoapp.entity.User;
import com.mertaksoy.todoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * This is our class that adds user to the database for testing when the application first boots up.
 */
@RequiredArgsConstructor
@Component
public class InitialConfig {

    @Value("${spring.couchbase.username}")
    private String couchbaseUsername;

    @Value("${spring.couchbase.password}")
    private String couchbasePassword;

    @Value("${spring.couchbase.connection-string}")
    private String couchbaseConnectionString;

    @Value("${spring.data.couchbase.bucket-name}")
    private String couchbaseBucketName;

    private final UserRepository userRepo;


    @PostConstruct
    private void initialValues() {
        // initCouchbaseBucket
        /*if (!cluster.buckets().getAllBuckets().containsKey(couchbaseBucketName)) {
            cluster.buckets().createBucket(
                    BucketSettings.create(couchbaseBucketName)
                            .bucketType(BucketType.COUCHBASE)
                            .minimumDurabilityLevel(DurabilityLevel.NONE)
                            .ramQuotaMB(2048));
        }*/

        // initUser
        if (userRepo.findByUsername("maksoy") == null) {
            User user = new User();
            user.setUsername("maksoy");
            user.setFirstName("Mert");
            user.setLastName("Aksoy");
            user.setPassword("mert123");

            userRepo.save(user);
        }
    }

    /*@Bean
    public Bucket getCouchbaseBucket(Cluster cluster) {
        //Creates the bucket if it does not exist yet
        if (!cluster.buckets().getAllBuckets().containsKey(couchbaseBucketName)) {
            cluster.buckets().createBucket(
                    BucketSettings.create(couchbaseBucketName)
                            .bucketType(BucketType.COUCHBASE)
                            .minimumDurabilityLevel(DurabilityLevel.NONE)
                            .ramQuotaMB(128));
        }
        return cluster.bucket(couchbaseBucketName);
    }*/

}
