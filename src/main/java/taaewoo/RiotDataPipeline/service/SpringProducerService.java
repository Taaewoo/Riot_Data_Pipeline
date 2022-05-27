package taaewoo.RiotDataPipeline.service;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
public class SpringProducerService {

    @Autowired
    private KafkaTemplate<String, JSONObject> kafkaTemplate;

    public String sendRiotDataMessage(String summonerName, JSONObject riotData) {

        riotData.put("whoseMatch", summonerName);

        JSONObject riotMetadata = (JSONObject) riotData.get("metadata");

        ListenableFuture<SendResult<String, JSONObject>> future = this.kafkaTemplate.send("summoner-match", riotData);

//        future.addCallback(new ListenableFutureCallback<SendResult<String, JSONObject>>() {
//
//            @Override
//            public void onSuccess(SendResult<String, JSONObject> result) {
//                System.out.println("Sent message=[" + summonerName + " : " + riotMetadata.get("matchId")  + "] " +
//                        "with offset=[" + result.getRecordMetadata().offset() + "]");
//            }
//
//            @Override
//            public void onFailure(Throwable ex) {
//                System.out.println("Unable to send message=[" + summonerName + " : " + riotMetadata.get("matchId") + "] " +
//                        "due to : " + ex.getMessage());
//            }
//        });

        return "success";
    }

}
