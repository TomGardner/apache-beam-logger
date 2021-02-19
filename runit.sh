mvn compile exec:java \
-Dexec.workingdir=target/classes \
-Dexec.mainClass=com.tomg.beam.logger.TxtToJsonLogger \
-Pdirect-runner -Dexec.args=" \
--project=txt-to-json-runner \
--runner=DirectRunner \
--stagingLocation=src/main/resources/ \
--inputFile=src/main/resources/input/*.txt \
--outputDir=src/main/resources/output/ \
--archiveDir=src/main/resources/archive/"
