package com.feiwanghub.subdataflownew.AggregationWithCoGroupBy;

import com.google.api.services.bigquery.model.TableRow;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

public class BuildRowFn extends DoFn<KV<String, Double>, TableRow> {

    @ProcessElement
    public void processElement(ProcessContext c) {
        KV<String, Double> element = c.element();

        TableRow row = new TableRow();
        row.set("counterparty", element.getKey());
        row.set("risk_value", element.getValue());
        c.output(row);
    }

}
