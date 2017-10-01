package com.semanticRelationsExtractorStanford.main;

import com.semanticRelationsExtractorStanford.data.SemanticExtractionData;
import com.semanticRelationsExtractorStanford.extraction.SemanticRelationsExtractor;
import com.semanticRelationsExtractorStanford.factories.InputDataFactory;
import com.semanticRelationsExtractorStanford.preprocessing.CapitalizedTokensPreprocessor;
import com.semanticRelationsExtractorStanford.preprocessing.SemanticPreprocessor;
import com.semanticRelationsExtractorStanford.tagger.PosTagger;
import com.semanticRelationsExtractorStanford.tokens.Tokenizer;
import com.semanticRelationsExtractorStanford.tokens.TokenizerImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oliver on 6/28/2017.
 */
public class SemanticExtractionExecutorTest {

    private InputDataFactory inputDataFactory;

    private CapitalizedTokensPreprocessor capitalizedTokensPreprocessor;

    private SemanticPreprocessor semanticPreprocessor;

    private SemanticRelationsExtractor semanticRelationsExtractor;

    private Tokenizer tokenizer;

    private SemanticExtractionExecutor semanticExtractionExecutor;

    private PosTagger posTagger = new PosTagger();

    private String executorPath = "C:\\Users\\Oliver\\Documents\\NlpTrainingData\\SemanticExtraction\\SemanticExtractionTestData.txt";

    @Before
    public void setup() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring_beans.xml");
        InputDataFactory inputDataFactory = (InputDataFactory) context.getBean("inputDataFactory");
        CapitalizedTokensPreprocessor capitalizedTokensPreprocessor = (CapitalizedTokensPreprocessor) context.getBean("capitalizedTokensPreprocessor");
        SemanticPreprocessor semanticPreprocessor = (SemanticPreprocessor) context.getBean("semanticPreprocessor");
        SemanticRelationsExtractor semanticRelationsExtractor = (SemanticRelationsExtractor) context.getBean("semanticRelationsExtractor");
        tokenizer = new TokenizerImpl();
        List<SemanticExtractionData> semanticExtractionDataList = new ArrayList<SemanticExtractionData>();
        semanticExtractionExecutor = new SemanticExtractionExecutorImpl(inputDataFactory, capitalizedTokensPreprocessor,
                posTagger, semanticPreprocessor, semanticRelationsExtractor, tokenizer, executorPath, semanticExtractionDataList);
    }

    @Test
    public void test1() {
        long startTime = System.currentTimeMillis();
        semanticExtractionExecutor.execute();
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("sentence processed " + "in " + (elapsedTime / 1000) / 60 + " minutes and "
                + (elapsedTime / 1000) % 60 + " seconds");
    }

}
