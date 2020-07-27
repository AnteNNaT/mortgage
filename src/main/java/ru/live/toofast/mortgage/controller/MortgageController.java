package ru.live.toofast.mortgage.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.live.toofast.mortgage.entity.ApplicationDeclineReason;
import ru.live.toofast.mortgage.entity.ApplicationStatus;
import ru.live.toofast.mortgage.entity.MortgageApplication;
import ru.live.toofast.mortgage.model.MortgageList;
import ru.live.toofast.mortgage.model.MortgageRequest;
import ru.live.toofast.mortgage.model.MortgageResponse;
import ru.live.toofast.mortgage.repository.MortgageApplicationRepository;
import ru.live.toofast.mortgage.service.CheckService;

import java.util.Optional;

@RestController
public class MortgageController {

    private final MortgageApplicationRepository repository;

    private final CheckService checkService;

    public MortgageController(MortgageApplicationRepository repository, CheckService checkService) {
        this.repository = repository;
        this.checkService = checkService;
    }

    @GetMapping("/mortgages")
    public MortgageList getAll(){
        return new MortgageList(repository.findAll());
    }

    @GetMapping("/mortgages/successful")
    public MortgageList getAllSuccessful(){
        return new MortgageList(repository.findAllByStatusEquals(ApplicationStatus.SUCCESS));
    }

    @GetMapping("/mortgages/declined")
    public MortgageList getAllDeclined(){
        return new MortgageList(repository.findAllByStatusEquals(ApplicationStatus.DECLINE));
    }


    @PostMapping("/mortgage")
    public MortgageResponse register(@RequestBody MortgageRequest request){

        MortgageApplication mortgageApplication = new MortgageApplication();
        mortgageApplication.setName(request.getName());
        mortgageApplication.setStatus(checkService.status(request));
        Optional<ApplicationDeclineReason> declineReason=checkService.declineReason(request);
        if (declineReason.isPresent()) {
            mortgageApplication.setDeclineReason(declineReason.get());
        }
        mortgageApplication.setPassportId(request.getPassport());
        mortgageApplication = repository.save(mortgageApplication);

        MortgageResponse response = new MortgageResponse();
        response.setRequest(request);
        response.setId(mortgageApplication.getId());
        response.setResolution(mortgageApplication.getStatus().toString());

        return response;
    }


}
