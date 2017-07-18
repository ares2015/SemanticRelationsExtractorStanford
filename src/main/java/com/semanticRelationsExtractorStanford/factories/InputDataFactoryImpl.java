package com.semanticRelationsExtractorStanford.factories;

import com.semanticRelationsExtractorStanford.data.InputData;
import com.semanticRelationsExtractorStanford.tokens.Tokenizer;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Oliver on 5/17/2017.
 */
public class InputDataFactoryImpl implements InputDataFactory {

    private final static Logger LOGGER = Logger.getLogger(InputDataFactoryImpl.class.getName());

    private Tokenizer tokenizer;

    private MultiListFactory multiListFactory;

    public InputDataFactoryImpl(Tokenizer tokenizer, MultiListFactory multiListFactory) {
        this.tokenizer = tokenizer;
        this.multiListFactory = multiListFactory;
    }

    @Override
    public InputData create(String sentence, List<String> tagSequencesList) {
        //LOGGER.info("ENTERING create method of InputDataFactoryImpl... ");
        //LOGGER.info("*********************************************************************");

        InputData inputData = new InputData();

        //LOGGER.info("Processing sentence < " + sentence);

        List<String> tokensList = tokenizer.splitStringIntoList(sentence);

        if (sentence.contains(", ")) {
            inputData.setContainsSubSentences(true);
            //MULTILISTS ARE CREATED FIRST BEFORE COMMAS ARE REMOVED FROM TOKENS LIST AND TAGS LIST
            //SUB SENTENCES MULTILIST
            List<List<String>> subSentencesMultiList = multiListFactory.create(tokensList);
            inputData.setTokensMultiList(subSentencesMultiList);

            //TAGS MULTILIST
            for (String tagsSubsequence : tagSequencesList) {
                List<String> tagsList = tokenizer.splitStringIntoList(tagsSubsequence);
                inputData.getTagsMultiList().add(tagsList);
            }
            //LOGGER.info("Sentence contains " + subSentencesMultiList.size() + " subSentences.");

        } else {
            inputData.setContainsSubSentences(false);
            //LOGGER.info("Sentence does not contain any subSentences.");

            inputData.setTokensList(tokensList);
            List<String> tagsList = tokenizer.splitStringIntoList(tagSequencesList.get(0));
            inputData.setTagsList(tagsList);
        }

        //LOGGER.info("LEAVING create method of SubPathDataListFactoryImpl... ");
        //LOGGER.info("*********************************************************************");

        return inputData;
    }

}