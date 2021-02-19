package com.tomg.beam.logger;

import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.Validation;

public interface Options extends PipelineOptions {
    @Description("Input file path")
    @Validation.Required
    String getInputFile();
    void setInputFile(String value);

    @Description("Output directory")
    @Validation.Required
    String getOutputDir();
    void setOutputDir(String value);

    @Description("Archive directory")
    @Validation.Required
    String getArchiveDir();
    void setArchiveDir(String value);

    @Description("Staging Location")
    @Validation.Required
    String getStagingLocation();
    void setStagingLocation(String value);

}
