package com.finanse.validator;

import com.finanse.dto.LoanScreenDto;
import com.finanse.model.Loan;
import com.finanse.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@PropertySource("classpath:/application.properties")
public class LoanRiskResearcher {

    @Value("${maximum.number.of.applications.per.day}")
    private Integer MAXIMUM_NUMBER_OF_APPLICATIONS_PER_DAY;

    @Value("${maximum.number.of.applications.per.day.message}")
    private String MAXIMUM_NUMBER_OF_APPLICATIONS_PER_DAY_ERROR_MESSAGE;

    @Value("${max.amount.of.loan}")
    private Integer MAX_AMOUNT;

    @Value("${max.amount.message}")
    private String MAX_AMOUNT_ERROR_MESSAGE;

    @Value("${time.risk.from.hour}")
    private String TIME_RISK_FROM_HOUR;

    @Value("${time.risk.to.hour}")
    private String TIME_RISK_TO_HOUR;

    private List<String> listRisks;

    @Autowired
    private LoanRepository repository;

    public List<String> research(LoanScreenDto loanScreenDto, String ip, Long userId) {
        listRisks = new ArrayList<>();
        getMaximumNumberAppPerDay(ip, userId);
        betweenMidnightAndSixAmWithMaxAmount(loanScreenDto);
        return listRisks;
    }

    private void betweenMidnightAndSixAmWithMaxAmount(LoanScreenDto loanScreenDto) {
        LocalTime localTime = LocalTime.now();
        LocalTime riskTimeFrom = LocalTime.of(Integer.valueOf(TIME_RISK_FROM_HOUR), 0);
        LocalTime riskTimeTo = LocalTime.of(Integer.valueOf(TIME_RISK_TO_HOUR), 0);

        if (localTime.isAfter(riskTimeFrom) && localTime.isBefore(riskTimeTo)
                && loanScreenDto.getAmount() > MAX_AMOUNT) {
            listRisks.add(String.format(MAX_AMOUNT_ERROR_MESSAGE, riskTimeFrom, riskTimeTo));
        }
    }

    private void getMaximumNumberAppPerDay(String ip, Long userId) {
        List<Loan> loans = repository.findByIpAddressAndIdUser(ip, userId);
        if (Objects.nonNull(loans) && loans.size() >= MAXIMUM_NUMBER_OF_APPLICATIONS_PER_DAY) {
            listRisks.add(MAXIMUM_NUMBER_OF_APPLICATIONS_PER_DAY_ERROR_MESSAGE);
        }
    }
}
