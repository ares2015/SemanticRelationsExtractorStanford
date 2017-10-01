package com.semanticRelationsExtractorStanford.writer;

import com.semanticRelationsExtractorStanford.data.SemanticExtractionData;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oliver on 10/1/2017.
 */
public class SemanticDataWriterTest {

    @Test
    public void test() {

        List<SemanticExtractionData> semanticExtractionDataList = new ArrayList<SemanticExtractionData>();

        SemanticExtractionData semanticExtractionData1 = new SemanticExtractionData();
        semanticExtractionData1.setAtomicSubject("swimming");
        semanticExtractionData1.setExtendedSubject("Typically swimming");
        semanticExtractionData1.setAtomicVerbPredicate("takes");
        semanticExtractionData1.setExtendedVerbPredicate("takes");
        semanticExtractionData1.setAtomicNounPredicate("place");
        semanticExtractionData1.setExtendedNounPredicate("place in pool");
        semanticExtractionData1.setSentence("Typically swimming takes place in pools");
        semanticExtractionData1.setObject("Swimming");

        SemanticExtractionData semanticExtractionData2 = new SemanticExtractionData();
        semanticExtractionData2.setAtomicSubject("football");
        semanticExtractionData2.setExtendedSubject("Typically football");
        semanticExtractionData2.setAtomicVerbPredicate("takes");
        semanticExtractionData2.setExtendedVerbPredicate("takes");
        semanticExtractionData2.setAtomicNounPredicate("place");
        semanticExtractionData2.setExtendedNounPredicate("place in field");
        semanticExtractionData2.setSentence("Typically football takes place in field\"");
        semanticExtractionData2.setObject("Football");

        semanticExtractionDataList.add(semanticExtractionData1);
        semanticExtractionDataList.add(semanticExtractionData2);
        SemanticDataWriter semanticDataWriter = new SemanticDataWriterImpl(semanticExtractionDataList,
                "C:\\Users\\Oliver\\Documents\\NlpTrainingData\\SemanticExtraction\\WikipediaSemanticExtractionDataTEST.csv", 5);

        semanticDataWriter.write();
    }
}
