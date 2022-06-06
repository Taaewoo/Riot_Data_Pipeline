package taaewoo.RiotDataPipeline.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@PropertySource(ignoreResourceNotFound = false, value = "classpath:riotApiKey.properties")
public class MatchService {

    @Value("${riot.api.key}")
    private String mykey;

    private JSONParser parser = new JSONParser();


    public JSONArray callRiotApiMatchesByPuuid(String puuid) {
        String serverUrl = "https://asia.api.riotgames.com";
        String fullUrl = serverUrl + "/lol/match/v5/matches/by-puuid/" + puuid + "/ids?start=0&count=100" + "&api_key=" + mykey;
        String apiResult = callRiotApi(fullUrl);

        if(apiResult == null){
            return null;
        }

        try {
            return (JSONArray) parser.parse(apiResult);

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject callRiotApiMatchInfoByMatchID(String matchID){
        String serverUrl = "https://asia.api.riotgames.com";
        String fullUrl = serverUrl + "/lol/match/v5/matches/" + matchID + "?api_key=" + mykey;
        String apiResult = callRiotApi(fullUrl);

        if(apiResult == null){
            return null;
        }

        try {
            return (JSONObject) parser.parse(apiResult);

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject callRiotApiMatchTimelineByMatchID(String matchID){
        String serverUrl = "https://asia.api.riotgames.com";
        String fullUrl = serverUrl + "/lol/match/v5/matches/" + matchID + "/timeline?api_key=" + mykey;
        String apiResult = callRiotApi(fullUrl);

        if(apiResult == null){
            return null;
        }

        try {
            return (JSONObject) parser.parse(apiResult);

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getMatchIDList(String puuid){
        JSONArray jsonArrayResult = callRiotApiMatchesByPuuid(puuid);
        ArrayList<String> matchIDsList = new ArrayList<>();

        if(jsonArrayResult == null){
            return null;
        }

        for (int i = 0; i < jsonArrayResult.size(); i++) {
            matchIDsList.add(jsonArrayResult.get(i).toString());
        }

        return matchIDsList;
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

            return EntityUtils.toString(entity);

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
