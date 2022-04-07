package taaewoo.RiotDataPipeline.dto;

import lombok.Data;

@Data
public class InfoDTO {
    private long gameCreation;
    private long gameDuration;
    private long gameEndTimestamp;
    private long gameId;
    private String gameMode;
    private String gameName;
    private long gameStartTimestamp;
    private String gameType;
    private String gameVersion;
    private int mapId;
    private ParticipantDTO[] participants;
    private String platformId;
    private int queueId;
    private TeamDTO[] teams;
    private String tournamentCode;
}
