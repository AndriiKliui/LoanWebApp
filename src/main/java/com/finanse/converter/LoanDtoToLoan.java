package com.finanse.converter;

import com.finanse.dto.LoanScreenDto;
import com.finanse.model.Loan;
import com.finanse.model.LoanStatus;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class LoanDtoToLoan {

    public static Loan convert(LoanScreenDto screenDto, String ip, Long userId, List<String> deniesMessage) {
        Loan loan = new Loan();
        loan.setIdUser(userId);
        loan.setAmount(screenDto.getAmount());
        loan.setIpAddress(ip);
        loan.setExpireDate(screenDto.getTerm());

        if (CollectionUtils.isEmpty(deniesMessage)) {
            loan.setStatus(LoanStatus.APPROVED);
        } else {
            loan.setStatus(LoanStatus.REJECTION);
            loan.setNote(deniesMessage.get(0));
        }

        return loan;
    }

}
