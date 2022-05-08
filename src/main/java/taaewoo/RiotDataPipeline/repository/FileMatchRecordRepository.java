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
    private ClassPathResource resource = new ClassPathResource("json/matchRecord.json");

    private JSONObject matchRecordJsonObj;
    private Map<String, LinkedList<String>> matchList;

    @Override
    public void loadMatchRecord() {
        try {
            matchRecordJsonObj = (JSONObject) new JSONParser().parse(new InputStreamReader(resource.getInputStream(), "UTF-8"));

            matchList = objectMapper.readValue(matchRecordJsonObj.toString(), new TypeReference<Map<String,LinkedList<String>>>() {});

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateMatchRecord(String summonerName, List<String> updatedMatchList) {

        updatedMatchList.addAll(matchList.get(summonerName));
        matchList.put(summonerName,(LinkedList<String>) updatedMatchList);

        try {
            String updatedMatchRecord = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(matchList);
            System.out.println(updatedMatchRecord);

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
        return matchList.get(summonerName).get(0);
    }

}
