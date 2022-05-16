package taaewoo.RiotDataPipeline.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import taaewoo.RiotDataPipeline.dto.CommonResponse;
import taaewoo.RiotDataPipeline.repository.FileMatchRecordRepository;
import taaewoo.RiotDataPipeline.repository.MatchRecordRepository;
import taaewoo.RiotDataPipeline.service.MatchService;
import taaewoo.RiotDataPipeline.service.SpringProducerService;
import taaewoo.RiotDataPipeline.service.SummonerService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
public class ProducerController {

    private final SpringProducerService producerService;
    private final SummonerService summonerService;
    private final MatchService matchService;
    private final MatchRecordRepository matchRecordRepository = new FileMatchRecordRepository();

    @PostMapping(value = "/produceString")
    @ResponseBody
    public CommonResponse produceString(JSONObject s){

        log.info(s.toString());

        String apiResult = producerService.sendRiotDataMessage(s);

        if(apiResult == null){
            return new CommonResponse(HttpStatus.INTERNAL_SERVER_ERROR, "MatchService 내 에러 or API 실패");
        }

        return new CommonResponse(HttpStatus.OK, "성공", apiResult);
    }

    @PostMapping(value = "/produceSummonerMatchInfo")
    @ResponseBody
    public CommonResponse produceMatchInfoBySummonerName(String summonerName){

        log.info(summonerName);

        String summonerPuuid = summonerService.getSummonerPuuidByName(summonerName);
        if(summonerPuuid == null){
            return new CommonResponse(HttpStatus.INTERNAL_SERVER_ERROR, "SummonerService 내 에러 or API 실패");
        }

        List<String> matchIDsList = matchService.getMatchIDList(summonerPuuid);
        if(matchIDsList == null){
            return new CommonResponse(HttpStatus.INTERNAL_SERVER_ERROR, "MatchService 내 에러 or API 실패");
        }

        if(!matchRecordRepository.loadMatchRecord()){
            return new CommonResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Match record json 파일 로드 실패.");
        }

        String lastMatchID = matchRecordRepository.getLastMatchID(summonerName);
        log.info("Summoner Name : " + summonerName);
        log.info("Last Match ID : " + lastMatchID);

        List<String> updatedMatchList = new LinkedList<String>();
        for(String matchID : matchIDsList){
            if (matchID.equals(lastMatchID)) {
                break;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            JSONObject matchInfo = matchService.callRiotApiMatchInfoByMatchID(matchID);
            if(matchInfo == null){
                log.warn("Data is Null");
                continue;
            }

            String produceResult = producerService.sendRiotDataMessage(matchInfo);
            if(produceResult == null){
                log.error(matchID + " Produce Fail");
            }

            updatedMatchList.add(matchID);
        }

        log.info("New Match IDs : " + updatedMatchList.toString());
        matchRecordRepository.updateMatchRecord(summonerName, updatedMatchList);

        return new CommonResponse(HttpStatus.OK, "성공", updatedMatchList);
    }
}
