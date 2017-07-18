package com.semanticRelationsExtractorStanford.main;

import com.semanticRelationsExtractorStanford.data.InputData;
import com.semanticRelationsExtractorStanford.data.SemanticExtractionData;
import com.semanticRelationsExtractorStanford.data.SemanticPreprocessingData;
import com.semanticRelationsExtractorStanford.database.DatabaseAccessor;
import com.semanticRelationsExtractorStanford.extraction.SemanticRelationsExtractor;
import com.semanticRelationsExtractorStanford.factories.InputDataFactory;
import com.semanticRelationsExtractorStanford.preprocessing.CapitalizedTokensPreprocessor;
import com.semanticRelationsExtractorStanford.preprocessing.SemanticPreprocessor;
import com.semanticRelationsExtractorStanford.tagger.PosTagger;
import com.semanticRelationsExtractorStanford.tokens.Tokenizer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

/**
 * Created by Oliver on 6/27/2017.
 */
public class SemanticExtractionExecutorImpl implements SemanticExtractionExecutor, Callable {

    private final static Logger LOGGER = Logger.getLogger(SemanticExtractionProcessorImpl.class.getName());

    private InputDataFactory inputDataFactory;

    private CapitalizedTokensPreprocessor capitalizedTokensPreprocessor;

    private PosTagger posTagger;

    private SemanticPreprocessor semanticPreprocessor;

    private SemanticRelationsExtractor semanticRelationsExtractor;

    private DatabaseAccessor databaseAccessor;

    private Tokenizer tokenizer;

    private String path;

    private Integer countSemanticallyProcessedSentences = 0;

    public SemanticExtractionExecutorImpl(InputDataFactory inputDataFactory,
                                          CapitalizedTokensPreprocessor capitalizedTokensPreprocessor, PosTagger posTagger,
                                          SemanticPreprocessor semanticPreprocessor, SemanticRelationsExtractor semanticRelationsExtractor,
                                          DatabaseAccessor databaseAccessor, Tokenizer tokenizer, String path) {
        this.inputDataFactory = inputDataFactory;
        this.capitalizedTokensPreprocessor = capitalizedTokensPreprocessor;
        this.posTagger = posTagger;
        this.semanticPreprocessor = semanticPreprocessor;
        this.semanticRelationsExtractor = semanticRelationsExtractor;
        this.databaseAccessor = databaseAccessor;
        this.tokenizer = tokenizer;
        this.path = path;
    }

    @Override
    public Object call() throws Exception {
        execute();
        return countSemanticallyProcessedSentences;
    }

    @Override
    public void execute() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            int nrOfProcessedSentences = 0;
            String inputDataString = br.readLine();
            while (inputDataString != null) {
                try {
                    nrOfProcessedSentences++;
                    String[] split = inputDataString.split("#");
                    String sentence = split[0];
                    String object = split[1];
                    if (tokenizer.splitStringIntoList(sentence).size() > 30) {
                        inputDataString = br.readLine();
                        continue;
                    }
                    System.out.println("Processing sentence: " + sentence);
                    List<String> tagSequences = posTagger.tag(sentence);
                    InputData inputData = inputDataFactory.create(sentence, tagSequences);
                    capitalizedTokensPreprocessor.process(inputData);
                    if (inputData.containsSubSentences()) {
                        for (int i = 0; i <= inputData.getTokensMultiList().size() - 1; i++) {
                            List<String> tokensList = inputData.getTokensMultiList().get(i);
                            List<String> tagsList = inputData.getTagsMultiList().get(i);
                            processSentence(tokensList, tagsList, sentence, object);
                        }
                    } else {
                        List<String> tagsList = inputData.getTagsList();
                        List<String> tokensList = inputData.getTokensList();
                        processSentence(tokensList, tagsList, sentence, object);
                    }
                    System.out.println(nrOfProcessedSentences + " were sentences read and processed");
                    inputDataString = br.readLine();
                } catch (Exception e) {
//                    System.out.println(e.toString());
                    inputDataString = br.readLine();
                    continue;
                }
            }
            System.out.println(countSemanticallyProcessedSentences + " sentences were semantically processed.");
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processSentence(List<String> tokensList, List<String> tagsList, String sentence, String object) {
        SemanticPreprocessingData semanticPreprocessingData = semanticPreprocessor.preprocess(tokensList, tagsList);
        if (semanticPreprocessingData.canGoToExtraction()) {
            countSemanticallyProcessedSentences++;
            SemanticExtractionData semanticExtractionData = semanticRelationsExtractor.extract(semanticPreprocessingData);
            semanticExtractionData.setSentence(sentence);
            semanticExtractionData.setObject(object);
            if (!databaseAccessor.existsSemanticDataInDatabase(semanticExtractionData)) {
                databaseAccessor.insertSemanticData(semanticExtractionData);
            }
        }
    }

}