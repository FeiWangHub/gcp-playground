package com.feiwanghub.subdataflownew.AggregationWithCoGroupBy.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

public class RiskProto {

    @Setter
    @Getter
    @Builder
    @EqualsAndHashCode
    public static class Risk {
        String riskId;
        String tradeId;
        String marketId;
        Double value;
    }

}
