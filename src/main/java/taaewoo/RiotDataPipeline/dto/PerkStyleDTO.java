package taaewoo.RiotDataPipeline.dto;

import lombok.Data;

@Data
public class PerkStyleDTO {
    private String description;
    private PerkStyleSelectionDTO[] selections;
    private int style;
}
