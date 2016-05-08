package com.finanse.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class LoanScreenDto {

    private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
    private static final String MORE_THEN_MESSAGE = "{less.then.message}";

    @NotNull(message = LoanScreenDto.NOT_BLANK_MESSAGE)
    @Min(value = 1, message = LoanScreenDto.MORE_THEN_MESSAGE)
    private Double amount;

    @NotNull(message = LoanScreenDto.NOT_BLANK_MESSAGE)
    private Date term;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getTerm() {
        return term;
    }

    public void setTerm(Date term) {
        this.term = term;
    }
}
