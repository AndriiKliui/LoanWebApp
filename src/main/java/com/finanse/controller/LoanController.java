package com.finanse.controller;

import com.finanse.dto.LoanScreenDto;
import com.finanse.model.Loan;
import com.finanse.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class LoanController {

    @Autowired
    private LoanService service;

    @RequestMapping(value = "loan", method = RequestMethod.GET)
    public String loan() {
        return "loan/loan";
    }

    @ResponseBody
    @RequestMapping(value = "loan", method = RequestMethod.POST)
    public String loanResearch(@Valid @RequestBody LoanScreenDto loanScreenDto, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        return service.requestForApprove(loanScreenDto, ip).name();
    }

    @RequestMapping(value = "loan/my", method = RequestMethod.GET)
    public ModelAndView userLoans() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Loan> loans = service.getPersonLoans(auth.getName());
        ModelAndView mav = new ModelAndView("loan/my-loans");
        mav.addObject("loans", loans);
        return mav;
    }
}
