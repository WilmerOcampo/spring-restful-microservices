package com.ocs.restfulweb.user.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPostRepository extends JpaRepository<User, Integer> {
}
