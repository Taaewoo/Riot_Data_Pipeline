package taaewoo.RiotDataPipeline.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Slf4j
@Repository
public class FileMatchRecordRepository implements MatchRecordRepository {

    private ObjectMapper objectMapper = new ObjectMapper();
    private String jsonPath = "json/matchRecord.json";
    private ClassPathResource resource = new ClassPathResource(jsonPath);

    private JSONObject matchRecordJsonObj;
    private Map<String, LinkedList<String>> matchList;

    @Override
    public boolean loadMatchRecord() {
        try {
            if(!resource.exists()){
                log.error("Json file(" + jsonPath + ") does not exist.");
                return false;
            }
            matchRecordJsonObj = (JSONObject) new JSONParser().parse(new InputStreamReader(resource.getInputStream(), "UTF-8"));

            matchList = objectMapper.readValue(matchRecordJsonObj.toString(), new TypeReference<Map<String,LinkedList<String>>>() {});

            return true;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void updateMatchRecord(String summonerName, List<String> updatedMatchList) {

        if(!matchList.containsKey(summonerName)){
            matchList.put(summonerName, new LinkedList<String>());
        }

        List<String> allMatchList = new LinkedList<String>();

        allMatchList.addAll(updatedMatchList);
        allMatchList.addAll(matchList.get(summonerName));
        matchList.put(summonerName,(LinkedList<String>) allMatchList);

        try {
            String updatedMatchRecord = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(matchList);
            log.info(updatedMatchRecord);

            FileWriter file = new FileWriter(resource.getFile().getAbsoluteFile());
            file.write(updatedMatchRecord);
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getLastMatchID(String summonerName) {
        if(!matchList.containsKey(summonerName)){
            return "";
        }

        return matchList.get(summonerName).get(0);
    }

}
