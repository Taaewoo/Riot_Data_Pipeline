package taaewoo.RiotDataPipeline.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import taaewoo.RiotDataPipeline.dto.CommonResponse;
import taaewoo.RiotDataPipeline.service.MatchService;
import taaewoo.RiotDataPipeline.service.SpringProducerService;
import taaewoo.RiotDataPipeline.service.SummonerService;

import java.util.ArrayList;


@Slf4j
@Controller
@RequiredArgsConstructor
public class ProducerController {

    private final SpringProducerService producerService;
    private final SummonerService summonerService;
    private final MatchService matchService;

    @PostMapping(value = "/produceString")
    @ResponseBody
    public CommonResponse produceString(String s){

        log.debug(s);

        String apiResult = producerService.sendRiotDataMessage(s);

        if(apiResult == null){
            return new CommonResponse(HttpStatus.INTERNAL_SERVER_ERROR, "MatchService 내 에러 or API 실패");
        }

        return new CommonResponse(HttpStatus.OK, "성공", apiResult);
    }

    @PostMapping(value = "/produceSummonerMatchInfo")
    @ResponseBody
    public CommonResponse produceMatchInfoBySummonerName(String summonerName){

        log.debug(summonerName);

        String summonerPuuid = summonerService.getSummonerPuuidByName(summonerName);

        if(summonerPuuid == null){
            return new CommonResponse(HttpStatus.INTERNAL_SERVER_ERROR, "SummonerService 내 에러 or API 실패");
        }

        ArrayList<String> matchIDsList = matchService.getMatchIDList(summonerPuuid);

        if(matchIDsList == null){
            return new CommonResponse(HttpStatus.INTERNAL_SERVER_ERROR, "MatchService 내 에러 or API 실패");
        }

        return new CommonResponse(HttpStatus.OK, "성공", matchIDsList);
    }
}
