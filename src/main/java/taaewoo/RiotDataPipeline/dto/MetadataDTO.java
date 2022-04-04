package taaewoo.RiotDataPipeline.dto;

import lombok.Data;

@Data
public class MetadataDTO {
    private String dataVersion;
    private String matchId;
    private String[] participants;
}
