package taaewoo.RiotDataPipeline.dto;

import lombok.Data;

@Data
public class PerksDTO {
    private PerkStatsDTO statPerks;
    private PerkStyleDTO[] styles;
}
