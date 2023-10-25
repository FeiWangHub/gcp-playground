package com.feiwanghub.subdataflownew.AggregationWithCoGroupBy;

import com.google.api.services.bigquery.model.TableRow;

import java.util.List;

public class MockData {

    public static List<TableRow> mockTradeRows() {
        TableRow tradeRow1 = new TableRow();
        tradeRow1.set("trade_id", "1");
        tradeRow1.set("ccy", "USD");
        tradeRow1.set("counterparty", "A");

        TableRow tradeRow2 = new TableRow();
        tradeRow2.set("trade_id", "2");
        tradeRow2.set("ccy", "USD");
        tradeRow2.set("counterparty", "B");

        TableRow tradeRow3 = new TableRow();
        tradeRow3.set("trade_id", "3");
        tradeRow3.set("ccy", "USD");
        tradeRow3.set("counterparty", "C");

        TableRow tradeRow4 = new TableRow();
        tradeRow4.set("trade_id", "4");
        tradeRow4.set("ccy", "USD");
        tradeRow4.set("counterparty", "D");

        TableRow tradeRow5 = new TableRow();
        tradeRow5.set("trade_id", "5");
        tradeRow5.set("ccy", "USD");
        tradeRow5.set("counterparty", "E");

        return List.of(tradeRow1, tradeRow2, tradeRow3, tradeRow4, tradeRow5);
    }

    public static List<TableRow> mockRiskRows() {
        TableRow riskRow1 = new TableRow();
        riskRow1.set("risk_id", "1");
        riskRow1.set("trade_id", "1");
        riskRow1.set("market_id", "1");
        riskRow1.set("value", 1.0);

        TableRow riskRow2 = new TableRow();
        riskRow2.set("risk_id", "2");
        riskRow2.set("trade_id", "1");
        riskRow2.set("market_id", "2");
        riskRow2.set("value", 2.0);

        TableRow riskRow3 = new TableRow();
        riskRow3.set("risk_id", "3");
        riskRow3.set("trade_id", "1");
        riskRow3.set("market_id", "3");
        riskRow3.set("value", 3.0);

        TableRow riskRow4 = new TableRow();
        riskRow4.set("risk_id", "4");
        riskRow4.set("trade_id", "2");
        riskRow4.set("market_id", "1");
        riskRow4.set("value", 4.0);

        TableRow riskRow5 = new TableRow();
        riskRow5.set("risk_id", "5");
        riskRow5.set("trade_id", "2");
        riskRow5.set("market_id", "2");
        riskRow5.set("value", 5.0);

        TableRow riskRow6 = new TableRow();
        riskRow6.set("risk_id", "6");
        riskRow6.set("trade_id", "3");
        riskRow6.set("market_id", "1");
        riskRow6.set("value", 6.0);

        TableRow riskRow7 = new TableRow();
        riskRow7.set("risk_id", "7");
        riskRow7.set("trade_id", "3");
        riskRow7.set("market_id", "2");
        riskRow7.set("value", 7.0);

        TableRow riskRow8 = new TableRow();
        riskRow8.set("risk_id", "8");
        riskRow8.set("trade_id", "3");
        riskRow8.set("market_id", "3");
        riskRow8.set("value", 8.0);

        return List.of(riskRow1, riskRow2, riskRow3, riskRow4, riskRow5, riskRow6, riskRow7, riskRow8);
    }
}
