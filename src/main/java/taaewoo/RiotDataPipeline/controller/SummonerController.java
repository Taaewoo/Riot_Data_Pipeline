package taaewoo.RiotDataPipeline.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import taaewoo.RiotDataPipeline.dto.CommonResponse;
import taaewoo.RiotDataPipeline.dto.SummonerDTO;
import taaewoo.RiotDataPipeline.service.SummonerService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SummonerController {

    private final SummonerService summonerService;

    @PostMapping(value = "/summonerByName")
    @ResponseBody
    public CommonResponse callSummonerByName(String summonerName){

        summonerName = summonerName.replaceAll(" ","%20");

        log.debug(summonerName);

        SummonerDTO apiResult = summonerService.callRiotAPISummonerByName(summonerName);

        return new CommonResponse(HttpStatus.OK, "성공", apiResult);
    }


}
