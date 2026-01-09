package com.ocs.restfulweb.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

//@RepositoryRestResource(exported = false) // Disabled auto generated controller
public interface PostJpaRepository extends JpaRepository<Post, UUID> {
    Optional<Post> findByUuidAndUserId(UUID uuid, int userId);
}
