package taaewoo.RiotDataPipeline.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SpringProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public String sendRiotDataMessage(String riotData) {

        this.kafkaTemplate.send("test3", riotData);

        return "success";
    }

}
