package com.educandoweb.cursomc.repositories;

import com.educandoweb.cursomc.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Integer> {
}
