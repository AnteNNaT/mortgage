package ru.live.toofast.mortgage.service;

import org.springframework.stereotype.Service;
import ru.live.toofast.mortgage.entity.ApplicationDeclineReason;
import ru.live.toofast.mortgage.entity.ApplicationStatus;
import ru.live.toofast.mortgage.model.MortgageRequest;

import java.util.Optional;

@Service
public class CheckService {

    private ComplianceChecker complianceChecker;
    private CreditScoreChecker creditScoreChecker;
    private PaymentAmountCalculator paymentAmountCalculator;

    public CheckService(ComplianceChecker complianceChecker,
                        CreditScoreChecker creditScoreChecker,
                        PaymentAmountCalculator paymentAmountCalculator) {
        this.complianceChecker = complianceChecker;
        this.creditScoreChecker = creditScoreChecker;
        this.paymentAmountCalculator = paymentAmountCalculator;
    }

   public Optional<ApplicationDeclineReason> declineReason(MortgageRequest request){
    if (!complianceChecker.check(request.getPassport())){
        return Optional.of(ApplicationDeclineReason.TERRORIST);
       }else if (!creditScoreChecker.check(request.getPassport())){
        return Optional.of(ApplicationDeclineReason.SCORING_FAILED);
    }else if (!paymentAmountCalculator.check(request.getPeriod(),request.getSalary(),request.getCreditAmount())){
        return Optional.of(ApplicationDeclineReason.LOW_SALARY);
    }
       return Optional.empty();
   }

    public ApplicationStatus status(MortgageRequest request){
        if (complianceChecker.check(request.getPassport())&&
        creditScoreChecker.check(request.getPassport())&&
        paymentAmountCalculator.check(request.getPeriod(),request.getSalary(),request.getCreditAmount())){
            return ApplicationStatus.SUCCESS;
        }
        else return ApplicationStatus.DECLINE;
    }
}
