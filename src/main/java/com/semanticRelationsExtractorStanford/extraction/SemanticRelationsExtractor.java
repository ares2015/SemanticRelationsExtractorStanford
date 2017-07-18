package com.semanticRelationsExtractorStanford.extraction;

import com.semanticRelationsExtractorStanford.data.SemanticExtractionData;
import com.semanticRelationsExtractorStanford.data.SemanticPreprocessingData;

/**
 * Created by Oliver on 2/17/2017.
 */
public interface SemanticRelationsExtractor {

    SemanticExtractionData extract(SemanticPreprocessingData semanticPreprocessingData);

}
