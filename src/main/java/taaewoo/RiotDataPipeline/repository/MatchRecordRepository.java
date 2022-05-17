package taaewoo.RiotDataPipeline.repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface MatchRecordRepository {

    boolean loadMatchRecord();

    void updateMatchRecord(String summonerName, List<String> updatedMatchList);

    String getLastMatchID(String summonerName);
}
