package taaewoo.RiotDataPipeline.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import java.io.IOException;

import taaewoo.RiotDataPipeline.dto.SummonerDTO;

@Slf4j
@Service
@PropertySource(ignoreResourceNotFound = false, value = "classpath:riotApiKey.properties")
public class SummonerService {

    @Value("${riot.api.key}")
    private String mykey;

    public String callRiotAPISummonerByName(String summonerName){
        String serverUrl = "https://kr.api.riotgames.com";
        String fullUrl = serverUrl + "/lol/summoner/v4/summoners/by-name/" + summonerName + "?api_key=" + mykey;

        return callRiotApi(fullUrl);
    }

    public String getSummonerPuuidByName(String summonerName) {
        String serverUrl = "https://kr.api.riotgames.com";
        String fullUrl = serverUrl + "/lol/summoner/v4/summoners/by-name/" + summonerName + "?api_key=" + mykey;

        try {
            JSONParser parser = new JSONParser();

            JSONObject jsonObjectResult = (JSONObject) parser.parse(callRiotApi(fullUrl));

            return jsonObjectResult.get("puuid").toString();

        }catch (ParseException e){
            e.printStackTrace();
            return null;
        }
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

            return result;

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
