package taaewoo.RiotDataPipeline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SpringProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public String sendRiotDataMessage(String riotData) {
        System.out.println(riotData);
        this.kafkaTemplate.send("test2", riotData);

        return "success";
    }

}
