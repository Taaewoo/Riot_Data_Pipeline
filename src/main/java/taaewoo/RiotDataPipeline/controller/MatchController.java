package taaewoo.RiotDataPipeline.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import taaewoo.RiotDataPipeline.service.MatchService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;


    @PostMapping(value = "/matchesByPuuid")
    @ResponseBody
    public String[] callMatchesByPuuid(String puuid){

        log.debug(puuid);

        String[] apiResult = matchService.callRiotAPIMatchesByPuuid(puuid);

        return apiResult;
    }
}
