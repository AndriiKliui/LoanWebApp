package com.finanse.repository;

import com.finanse.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByIpAddressAndIdUser(String IpAddress, Long IdUser);

}
