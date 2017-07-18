package com.semanticRelationsExtractorStanford.extraction.predicate.noun;

import com.semanticRelationsExtractorStanford.data.SemanticExtractionData;
import com.semanticRelationsExtractorStanford.data.SemanticPreprocessingData;

/**
 * Created by Oliver on 2/16/2017.
 */
public interface NounPredicateExtractor {

    void extract(SemanticExtractionData semanticExtractionData, SemanticPreprocessingData semanticPreprocessingData);

}
