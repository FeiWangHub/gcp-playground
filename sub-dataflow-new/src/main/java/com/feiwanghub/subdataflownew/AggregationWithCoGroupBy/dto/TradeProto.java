package com.feiwanghub.subdataflownew.AggregationWithCoGroupBy.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

public class TradeProto {

    @Getter
    @Setter
    @Builder
    @EqualsAndHashCode
    public static class Trade {
        String tradeId;
        String ccy;
        String counterparty;
    }

}
