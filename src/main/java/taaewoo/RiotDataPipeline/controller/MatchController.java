package taaewoo.RiotDataPipeline.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import taaewoo.RiotDataPipeline.dto.CommonResponse;
import taaewoo.RiotDataPipeline.service.MatchService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @PostMapping(value = "/matchesByPuuid")
    @ResponseBody
    public CommonResponse callMatchesByPuuid(String puuid){

        log.debug(puuid);

        String[] apiResult = matchService.callRiotAPIMatchesByPuuid(puuid);

        if(apiResult == null){
            return new CommonResponse(HttpStatus.INTERNAL_SERVER_ERROR, "MatchService 내 에러 or API 실패");
        }

        return new CommonResponse(HttpStatus.OK, "성공", apiResult);
    }
}
