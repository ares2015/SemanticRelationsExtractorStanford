package com.semanticRelationsExtractorStanford.preprocessing;

import com.semanticRelationsExtractorStanford.data.SemanticPreprocessingData;

import java.util.List;

/**
 * Created by Oliver on 2/17/2017.
 */
public interface SemanticPreprocessor {

    SemanticPreprocessingData preprocess(List<String> tokens, List<String> tags);

}
