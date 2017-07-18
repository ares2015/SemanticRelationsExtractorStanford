package com.semanticRelationsExtractorStanford.extraction.predicate.preposition;

import com.semanticRelationsExtractorStanford.data.SemanticExtractionData;
import com.semanticRelationsExtractorStanford.data.SemanticPreprocessingData;

import java.util.List;

/**
 * Created by Oliver on 6/1/2017.
 */
public class PrepositionPredicateExtractorImpl implements PrepositionPredicateExtractor {

    @Override
    public void extract(SemanticExtractionData semanticExtractionData, SemanticPreprocessingData semanticPreprocessingData) {
        if (semanticPreprocessingData.getAfterVerbFirstPrepositionIndex() > -1) {
            int extractionStartIndex = semanticPreprocessingData.getAfterVerbFirstPrepositionIndex();
            List<String> tokensList = semanticPreprocessingData.getTokensList();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = extractionStartIndex; i <= tokensList.size() - 1; i++) {
                stringBuilder.append(tokensList.get(i));
                stringBuilder.append(" ");
            }
            semanticExtractionData.setPrepositionPredicate(stringBuilder.toString());
        }
    }

}
