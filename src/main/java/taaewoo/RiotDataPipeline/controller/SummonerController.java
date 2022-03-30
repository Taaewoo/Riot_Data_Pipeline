package taaewoo.RiotDataPipeline.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import taaewoo.RiotDataPipeline.dto.SummonerDTO;
import taaewoo.RiotDataPipeline.service.SummonerService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SummonerController {

    private final SummonerService summonerService;

    @PostMapping(value = "/summonerByName")
    @ResponseBody
    public SummonerDTO callSummonerByName(String summonerName){

        summonerName = summonerName.replaceAll(" ","%20");

        log.debug(summonerName);

        SummonerDTO apiResult = summonerService.callRiotAPISummonerByName(summonerName);

        return apiResult;
    }


}
