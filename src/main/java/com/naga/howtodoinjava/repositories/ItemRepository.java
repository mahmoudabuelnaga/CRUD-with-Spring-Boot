package com.naga.howtodoinjava.repositories;

import com.naga.howtodoinjava.persistences.ItemPersistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<ItemPersistence, Long> {
}
