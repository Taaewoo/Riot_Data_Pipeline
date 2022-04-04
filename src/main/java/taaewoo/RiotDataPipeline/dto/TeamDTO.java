package taaewoo.RiotDataPipeline.dto;

import lombok.Data;

@Data
public class TeamDTO {
    private BanDTO[] bans;
    private ObjectivesDTO objectives;
    private int teamID;
    private boolean win;
}
