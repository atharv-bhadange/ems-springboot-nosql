package com.employwise.assignment.employeeservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

@Configuration
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

        @Override
        public String getConnectionString() {
            return "localhost";
        }

        @Override
        public String getUserName() {
            return "Administrator";
        }

        @Override
        public String getPassword() {
            return "password";
        }

        @Override
        public String getBucketName() {
            return "employee";
        }
}
