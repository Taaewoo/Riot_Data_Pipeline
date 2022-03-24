package taaewoo.RiotDataPipeline.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import taaewoo.RiotDataPipeline.dto.SummonerDTO;
import taaewoo.RiotDataPipeline.service.SummonerService;

@Controller
@RequiredArgsConstructor
public class SummonerController {

    private final SummonerService summonerService;

    @PostMapping(value = "/summonerByName")
    @ResponseBody
    public SummonerDTO callSummonerByName(String summonerName){

        SummonerDTO apiResult = summonerService.callRiotAPISummonerByName(summonerName);

        return apiResult;
    }


}
