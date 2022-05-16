package taaewoo.RiotDataPipeline.service;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SpringProducerService {

    @Autowired
    private KafkaTemplate<String, JSONObject> kafkaTemplate;

    public String sendRiotDataMessage(String summonerName, JSONObject riotData) {

        riotData.put("whoseMatch", summonerName);

        System.out.println(riotData);

        this.kafkaTemplate.send("summoner-match", riotData);

        return "success";
    }

}
