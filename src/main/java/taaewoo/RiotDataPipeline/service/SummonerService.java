package taaewoo.RiotDataPipeline.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import taaewoo.RiotDataPipeline.dto.SummonerDTO;

import java.io.IOException;

@Service
@PropertySource(ignoreResourceNotFound = false, value = "classpath:riotApiKey.properties")
public class SummonerService {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${riot.api.key}")
    private String mykey;

    public SummonerDTO callRiotAPISummonerByName(String summonerName){

        SummonerDTO result;

        String serverUrl = "https://kr.api.riotgames.com";

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "/lol/summoner/v4/summoners/by-name/" + summonerName + "?api_key=" + mykey);

            System.out.println(serverUrl + summonerName + "?api_key=" + mykey);

            HttpResponse response = client.execute(request);

            if(response.getStatusLine().getStatusCode() != 200){
                System.out.println(response.getStatusLine().getStatusCode() + " not success!!@#!");
                return null;
            }

            HttpEntity entity = response.getEntity();
            result = objectMapper.readValue(entity.getContent(), SummonerDTO.class);
            System.out.println(result);

        } catch (IOException e){
            System.out.println("IOException!!!!");
            e.printStackTrace();
            return null;
        }

        return result;
    }
}
