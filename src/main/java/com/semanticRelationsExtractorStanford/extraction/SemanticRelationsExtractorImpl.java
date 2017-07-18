package com.semanticRelationsExtractorStanford.extraction;


import com.semanticRelationsExtractorStanford.data.SemanticExtractionData;
import com.semanticRelationsExtractorStanford.data.SemanticPreprocessingData;
import com.semanticRelationsExtractorStanford.extraction.predicate.noun.NounPredicateExtractor;
import com.semanticRelationsExtractorStanford.extraction.predicate.preposition.PrepositionPredicateExtractor;
import com.semanticRelationsExtractorStanford.extraction.predicate.verb.VerbPredicateExtractor;
import com.semanticRelationsExtractorStanford.extraction.subject.SubjectExtractor;

/**
 * Created by Oliver on 2/17/2017.
 */
public class SemanticRelationsExtractorImpl implements SemanticRelationsExtractor {

    private SubjectExtractor subjectExtractor;

    private VerbPredicateExtractor verbPredicateExtractor;

    private NounPredicateExtractor nounPredicateExtractor;

    private PrepositionPredicateExtractor prepositionPredicateExtractor;

    public SemanticRelationsExtractorImpl(SubjectExtractor subjectExtractor, VerbPredicateExtractor verbPredicateExtractor,
                                          NounPredicateExtractor nounPredicateExtractor,
                                          PrepositionPredicateExtractor prepositionPredicateExtractor) {
        this.subjectExtractor = subjectExtractor;
        this.verbPredicateExtractor = verbPredicateExtractor;
        this.nounPredicateExtractor = nounPredicateExtractor;
        this.prepositionPredicateExtractor = prepositionPredicateExtractor;
    }

    @Override
    public SemanticExtractionData extract(SemanticPreprocessingData semanticPreprocessingData) {
        SemanticExtractionData semanticExtractionData = new SemanticExtractionData();
        subjectExtractor.extract(semanticExtractionData, semanticPreprocessingData);
        verbPredicateExtractor.extract(semanticExtractionData, semanticPreprocessingData);
        nounPredicateExtractor.extract(semanticExtractionData, semanticPreprocessingData);
        prepositionPredicateExtractor.extract(semanticExtractionData, semanticPreprocessingData);
        return semanticExtractionData;
    }

}
