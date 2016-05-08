package com.finanse.service;

import com.finanse.converter.LoanDtoToLoan;
import com.finanse.dto.LoanScreenDto;
import com.finanse.exception.ServiceLayerException;
import com.finanse.model.Loan;
import com.finanse.model.LoanStatus;
import com.finanse.model.User;
import com.finanse.repository.LoanRepository;
import com.finanse.repository.UserRepository;
import com.finanse.validator.LoanRiskResearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LoanService {

    @Autowired
    private LoanRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanRiskResearcher loanRiskResearcher;

    @Transactional
    public LoanStatus requestForApprove(LoanScreenDto loanDto, String userIp) {
        List<String> deniesMessage;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findOneByEmail(auth.getName());

        deniesMessage = loanRiskResearcher.research(loanDto, userIp, user.getId());
        Loan loan = LoanDtoToLoan.convert(loanDto, userIp, user.getId(), deniesMessage);

        repository.save(loan);
        return loan.getStatus();
    }

    @Transactional
    public List<Loan> getPersonLoans(String userEmail) {
        User user = userRepository.findOneByEmail(userEmail);

        if (Objects.isNull(user)) {
            throw new ServiceLayerException("User was not found.");
        }

        //pre fetching
        List<Loan> loans = user.getLoans();
        for (Loan loan : loans) {
            loan.getAmount();
        }
        return loans;
    }

}
