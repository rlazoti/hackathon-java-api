package com.rodrigolazoti.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigolazoti.auth.models.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
  Authority findByName(String name);
}
