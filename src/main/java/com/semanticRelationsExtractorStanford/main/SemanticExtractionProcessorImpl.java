package com.semanticRelationsExtractorStanford.main;


import com.semanticRelationsExtractorStanford.data.SemanticExtractionData;
import com.semanticRelationsExtractorStanford.extraction.SemanticRelationsExtractor;
import com.semanticRelationsExtractorStanford.factories.InputDataFactory;
import com.semanticRelationsExtractorStanford.preprocessing.CapitalizedTokensPreprocessor;
import com.semanticRelationsExtractorStanford.preprocessing.SemanticPreprocessor;
import com.semanticRelationsExtractorStanford.tagger.PosTagger;
import com.semanticRelationsExtractorStanford.tokens.Tokenizer;
import com.semanticRelationsExtractorStanford.writer.SemanticDataWriter;
import com.semanticRelationsExtractorStanford.writer.SemanticDataWriterImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Logger;


/**
 * Created by Oliver on 5/15/2017.
 */
public class SemanticExtractionProcessorImpl implements SemanticExtractionProcessor {

    private InputDataFactory inputDataFactory;

    private PosTagger posTagger = new PosTagger();

    private CapitalizedTokensPreprocessor capitalizedTokensPreprocessor;

    private SemanticPreprocessor semanticPreprocessor;

    private SemanticRelationsExtractor semanticRelationsExtractor;

    private Tokenizer tokenizer;

    private String executor1Path = "C:\\Users\\Oliver\\Documents\\NlpTrainingData\\SemanticExtraction\\WikipediaRawTextData1.txt";

    private String executor2Path = "C:\\Users\\Oliver\\Documents\\NlpTrainingData\\SemanticExtraction\\WikipediaRawTextData2.txt";

    private String executor3Path = "C:\\Users\\Oliver\\Documents\\NlpTrainingData\\SemanticExtraction\\WikipediaRawTextData3.txt";

    private String executor4Path = "C:\\Users\\Oliver\\Documents\\NlpTrainingData\\SemanticExtraction\\WikipediaRawTextData4.txt";

    private String writerPath = "C:\\Users\\Oliver\\Documents\\NlpTrainingData\\SemanticExtraction\\WikipediaSemanticExtractionData.csv";

    private List<SemanticExtractionData> semanticExtractionDataList = new ArrayList<SemanticExtractionData>();

    private final static Logger LOGGER = Logger.getLogger(SemanticExtractionProcessorImpl.class.getName());

    public SemanticExtractionProcessorImpl(InputDataFactory inputDataFactory,
                                           CapitalizedTokensPreprocessor capitalizedTokensPreprocessor,
                                           SemanticPreprocessor semanticPreprocessor, SemanticRelationsExtractor semanticRelationsExtractor,
                                           Tokenizer tokenizer) {
        this.inputDataFactory = inputDataFactory;
        this.capitalizedTokensPreprocessor = capitalizedTokensPreprocessor;
        this.semanticPreprocessor = semanticPreprocessor;
        this.semanticRelationsExtractor = semanticRelationsExtractor;
        this.tokenizer = tokenizer;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, FileNotFoundException {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring_beans.xml");
        InputDataFactory inputDataFactory = (InputDataFactory) context.getBean("inputDataFactory");
        CapitalizedTokensPreprocessor capitalizedTokensPreprocessor = (CapitalizedTokensPreprocessor) context.getBean("capitalizedTokensPreprocessor");
        SemanticPreprocessor semanticPreprocessor = (SemanticPreprocessor) context.getBean("semanticPreprocessor");
        SemanticRelationsExtractor semanticRelationsExtractor = (SemanticRelationsExtractor) context.getBean("semanticRelationsExtractor");
        Tokenizer tokenizer = (Tokenizer) context.getBean("tokenizer");
        SemanticExtractionProcessor semanticExtractionProcessor = new SemanticExtractionProcessorImpl(inputDataFactory, capitalizedTokensPreprocessor, semanticPreprocessor,
                semanticRelationsExtractor, tokenizer);

        semanticExtractionProcessor.process();
    }


    @Override
    public void process() throws InterruptedException, ExecutionException, FileNotFoundException {
        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(4);

//        SemanticExtractionExecutor semanticExtractionExecutor1 = new SemanticExtractionExecutorImpl(inputDataFactory, capitalizedTokensPreprocessor,
//                posTagger, semanticPreprocessor, semanticRelationsExtractor, databaseAccessor, tokenizer, executor1Path);
//        semanticExtractionExecutor1.execute();
//
//        SemanticExtractionExecutor semanticExtractionExecutor2 = new SemanticExtractionExecutorImpl(inputDataFactory, capitalizedTokensPreprocessor,
//                posTagger, semanticPreprocessor, semanticRelationsExtractor, databaseAccessor, tokenizer, executor2Path);
//        semanticExtractionExecutor2.execute();
//
//        SemanticExtractionExecutor semanticExtractionExecutor3 = new SemanticExtractionExecutorImpl(inputDataFactory, capitalizedTokensPreprocessor,
//                posTagger, semanticPreprocessor, semanticRelationsExtractor, databaseAccessor, tokenizer, executor3Path);
//        semanticExtractionExecutor3.execute();
//
//        SemanticExtractionExecutor semanticExtractionExecutor4 = new SemanticExtractionExecutorImpl(inputDataFactory, capitalizedTokensPreprocessor,
//                posTagger, semanticPreprocessor, semanticRelationsExtractor, databaseAccessor, tokenizer, executor4Path);
//        semanticExtractionExecutor4.execute();

        Callable semanticExtractionExecutor1 = new SemanticExtractionExecutorImpl(inputDataFactory, capitalizedTokensPreprocessor,
                posTagger, semanticPreprocessor, semanticRelationsExtractor, tokenizer, executor1Path, semanticExtractionDataList);

        Callable semanticExtractionExecutor2 = new SemanticExtractionExecutorImpl(inputDataFactory, capitalizedTokensPreprocessor,
                posTagger, semanticPreprocessor, semanticRelationsExtractor, tokenizer, executor2Path, semanticExtractionDataList);

        Callable semanticExtractionExecutor3 = new SemanticExtractionExecutorImpl(inputDataFactory, capitalizedTokensPreprocessor,
                posTagger, semanticPreprocessor, semanticRelationsExtractor, tokenizer, executor3Path, semanticExtractionDataList);

        Callable semanticExtractionExecutor4 = new SemanticExtractionExecutorImpl(inputDataFactory, capitalizedTokensPreprocessor,
                posTagger, semanticPreprocessor, semanticRelationsExtractor, tokenizer, executor4Path, semanticExtractionDataList);

        Future future1 = executor.submit(semanticExtractionExecutor1);
        Future future2 = executor.submit(semanticExtractionExecutor2);
        Future future3 = executor.submit(semanticExtractionExecutor3);
        Future future4 = executor.submit(semanticExtractionExecutor4);

        boolean areDataProcessed = false;
        while (!areDataProcessed) {
            areDataProcessed = !future1.isDone() && !future2.isDone() && !future3.isDone() && !future4.isDone();
        }

        if (areDataProcessed) {
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            int numberOfProcessedSentences = (Integer) future1.get() + (Integer) future2.get() + (Integer) future3.get() + (Integer) future4.get();
            LOGGER.info(numberOfProcessedSentences + " sentences were processed " + "in " + (elapsedTime / 1000) / 60 + " minutes and "
                    + (elapsedTime / 1000) % 60 + " seconds");
            SemanticDataWriter semanticDataWriter = new SemanticDataWriterImpl(semanticExtractionDataList, writerPath, 3692120);
            semanticDataWriter.write();

        }
    }

}