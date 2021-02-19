package com.tomg.beam.logger;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Arrays;
import java.util.List;


public class TxtToJsonLogger {

    private static Log logger = LogFactory.getLog(TxtToJsonLogger.class);

    static final TupleTag<String> outputRouteOne = new TupleTag<String>(){};
    static final TupleTag<String> outputRouteTwo = new TupleTag<String>(){};
    static final TupleTag<String> archive = new TupleTag<String>(){};


    public static void main(String[] args) {

        try {

            Options options = PipelineOptionsFactory.fromArgs(args).withValidation().as(Options.class);
            Pipeline p = Pipeline.create(options);

            PCollectionTuple results = p
                    .apply("Read Log File", TextIO.read().from(options.getInputFile()))
                    .apply(ParDo.of(new BuildRowFn())
                        .withOutputTags(outputRouteOne, TupleTagList.of(outputRouteTwo).and(archive)))
                    ;
            results.get(outputRouteOne).apply(TextIO.write().to(options.getOutputDir() + "routeone/output1.txt").withoutSharding());
            results.get(outputRouteTwo).apply(TextIO.write().to(options.getOutputDir() + "routetwo/output2.txt").withoutSharding());
            results.get(archive).apply(TextIO.write().to(options.getArchiveDir() + "archive.txt").withoutSharding());
            p.run().waitUntilFinish();

        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("main: Exception: " + e.getMessage());
        }

    }

    protected static class BuildRowFn extends DoFn<String, String> {

        @ProcessElement
        public void processElement(@Element String line, MultiOutputReceiver out) {

            List<String> fields = Arrays.asList(line.split("\\s"));

            LogStream logStream = new LogStream(
                    fields.get(0),
                    fields.get(1),
                    fields.get(2),
                    Integer.valueOf(fields.get(3)),
                    Integer.valueOf(fields.get(4)),
                    Integer.valueOf(fields.get(5)),
                    Boolean.valueOf(fields.get(6)),
                    Long.valueOf(fields.get(7))
                    );

            if (logStream.getLogId() == 1) {
                out.get(outputRouteOne).output(logStream.toString());
            } else if (logStream.getLogId() == 2) {
                out.get(outputRouteTwo).output(logStream.toString());
            }
            out.get(archive).output(logStream.toString());
        }
    }


}
