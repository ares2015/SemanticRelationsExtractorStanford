package com.semanticRelationsExtractorStanford.writer;

import com.semanticRelationsExtractorStanford.data.SemanticExtractionData;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Oliver on 10/1/2017.
 */
public class SemanticDataWriterImpl implements SemanticDataWriter {

    private List<SemanticExtractionData> semanticExtractionDataList;

    private String path;

    private int id;


    public SemanticDataWriterImpl(List<SemanticExtractionData> semanticExtractionDataList, String path, int id) {
        this.semanticExtractionDataList = semanticExtractionDataList;
        this.path = path;
        this.id = id;
    }

    @Override
    public void write() {

        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter(path, true);
            bw = new BufferedWriter(fw);
            for (SemanticExtractionData semanticExtractionData : semanticExtractionDataList) {
                String semanticDataRow = "";
                try {
                    semanticDataRow = "\"" + id + "\"" + "," +
                            "\"" + removeDoubleQuotes(semanticExtractionData.getAtomicSubject()) + "\"" + "," +
                            "\"" + removeDoubleQuotes(semanticExtractionData.getExtendedSubject()) + "\"" + "," +
                            "\"" + removeDoubleQuotes(semanticExtractionData.getAtomicVerbPredicate()) + "\"" + "," +
                            "\"" + removeDoubleQuotes(semanticExtractionData.getExtendedVerbPredicate()) + "\"" + "," +
                            "\"" + removeDoubleQuotes(semanticExtractionData.getAtomicNounPredicate()) + "\"" + "," +
                            "\"" + removeDoubleQuotes(semanticExtractionData.getExtendedNounPredicate()) + "\"" + "," +
                            "\"" + removeDoubleQuotes(semanticExtractionData.getSentence()) + "\"" + "," +
                            "\"" + removeDoubleQuotes(semanticExtractionData.getObject()) + "\"";
                } catch (Exception e) {
                    continue;
                }
                bw.write(semanticDataRow);
                id++;
                System.out.println("Writing into WikipediaSemanticExtractionData file: " + semanticDataRow);
                bw.newLine();
            }
            System.out.println("Writing into WikipediaSemanticExtractionData file finished");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private String removeDoubleQuotes(String string) {
        if (string.contains("\"")) {
            string = string.replace("\"", "");
        }
        return string;
    }
}
