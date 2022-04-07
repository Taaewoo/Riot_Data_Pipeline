package taaewoo.RiotDataPipeline.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@PropertySource(ignoreResourceNotFound = false, value = "classpath:riotApiKey.properties")
public class MatchService {

    @Value("${riot.api.key}")
    private String mykey;

    public String callRiotApiMatchesByPuuid(String puuid) {
        String serverUrl = "https://asia.api.riotgames.com";
        String fullUrl = serverUrl + "/lol/match/v5/matches/by-puuid/" + puuid + "/ids?start=0&count=100" + "&api_key=" + mykey;

        return callRiotApi(fullUrl);
    }

    public String callRiotApiMatchInfoByMatchID(String matchID){
        String serverUrl = "https://asia.api.riotgames.com";
        String fullUrl = serverUrl + "/lol/match/v5/matches/" + matchID + "?api_key=" + mykey;

        return callRiotApi(fullUrl);
    }

    public String callRiotApiMatchTimelineByMatchID(String matchID){
        String serverUrl = "https://asia.api.riotgames.com";
        String fullUrl = serverUrl + "/lol/match/v5/matches/" + matchID + "/timeline?api_key=" + mykey;

        return callRiotApi(fullUrl);
    }

    public String callRiotApi(String url){
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);

            HttpResponse response = client.execute(request);

            if(response.getStatusLine().getStatusCode() != 200){
                return null;
            }

            HttpEntity entity = response.getEntity();

            String result = EntityUtils.toString(entity);

            log.debug(result);

            return result;

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
