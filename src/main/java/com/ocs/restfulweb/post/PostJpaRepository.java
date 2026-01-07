package com.ocs.restfulweb.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostJpaRepository extends JpaRepository<Post, UUID> {
}
