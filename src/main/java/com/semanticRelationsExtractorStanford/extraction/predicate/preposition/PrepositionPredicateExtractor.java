package com.semanticRelationsExtractorStanford.extraction.predicate.preposition;

import com.semanticRelationsExtractorStanford.data.SemanticExtractionData;
import com.semanticRelationsExtractorStanford.data.SemanticPreprocessingData;

/**
 * Created by Oliver on 6/1/2017.
 */
public interface PrepositionPredicateExtractor {

    void extract(SemanticExtractionData semanticExtractionData, SemanticPreprocessingData semanticPreprocessingData);

}
