package com.semanticRelationsExtractorStanford.extraction.predicate.verb;

import com.semanticRelationsExtractorStanford.data.SemanticExtractionData;
import com.semanticRelationsExtractorStanford.data.SemanticPreprocessingData;

/**
 * Created by Oliver on 2/17/2017.
 */
public interface VerbPredicateExtractor {

    void extract(SemanticExtractionData semanticExtractionData, SemanticPreprocessingData semanticPreprocessingData);

}
