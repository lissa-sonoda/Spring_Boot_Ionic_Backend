package com.educandoweb.cursomc.repositories;

import com.educandoweb.cursomc.domain.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Integer> {
}
