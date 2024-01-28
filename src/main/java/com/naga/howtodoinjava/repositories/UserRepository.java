package com.naga.howtodoinjava.repositories;

import com.naga.howtodoinjava.persistences.UserPersistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<UserPersistence, UUID> {
}
