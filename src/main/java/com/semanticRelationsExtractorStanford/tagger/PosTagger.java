package com.semanticRelationsExtractorStanford.tagger;

import com.semanticRelationsExtractorStanford.cache.ConstantWordsCache;
import com.semanticRelationsExtractorStanford.cache.TagsConversionCache;
import com.semanticRelationsExtractorStanford.morphology.NumberPrefixDetector;
import com.semanticRelationsExtractorStanford.morphology.NumberPrefixDetectorImpl;
import com.semanticRelationsExtractorStanford.tags.StanfordTags;
import com.semanticRelationsExtractorStanford.tags.Tags;
import com.semanticRelationsExtractorStanford.tokens.Tokenizer;
import com.semanticRelationsExtractorStanford.tokens.TokenizerImpl;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Created by Oliver on 7/18/2017.
 */
public class PosTagger {

    private Properties props;

    private StanfordCoreNLP pipeline;

    private Tokenizer tokenizer = new TokenizerImpl();

    private NumberPrefixDetector numberPrefixDetector = new NumberPrefixDetectorImpl();

    public PosTagger() {
        props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos");
        pipeline = new StanfordCoreNLP(props);
    }


    public List<String> tag(String inputSentence) {
        List<String> tagSequences = new ArrayList<>();
        List<String> tokens = tokenizer.getTokens(inputSentence);
        Set<Integer> commaIndexes = tokenizer.getCommaIndexes(tokens);
        int sentenceLength = tokens.size();
        StringBuilder stringBuilder = new StringBuilder();
        Annotation annotation = new Annotation(inputSentence);
        pipeline.annotate(annotation);
        if (annotation.get(CoreAnnotations.SentencesAnnotation.class).size() > 0) {
            CoreMap processedSentence = annotation.get(CoreAnnotations.SentencesAnnotation.class).get(0);
            int index = 0;
            for (CoreLabel stfToken : processedSentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = stfToken.get(CoreAnnotations.TextAnnotation.class);
                String tag = stfToken.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                if (!",".equals(tag) && !StanfordTags.POSSESIVE_ENDING.equals(tag) && !"n't".equals(word)) {
                    if (numberPrefixDetector.detect(word)) {
                        stringBuilder.append(Tags.NUMBER);
                        if (commaIndexes.contains(index)) {
                            tagSequences.add(stringBuilder.toString());
                            stringBuilder.setLength(0);
                        }
                        if (index <= sentenceLength - 1) {
                            stringBuilder.append(" ");
                        }
                    } else {
                        if (index > 0 && !Tags.PRONOUN_PERSONAL.equals(tag) && Character.isUpperCase(word.charAt(0))) {
                            stringBuilder.append(Tags.NOUN);
                            if (commaIndexes.contains(index)) {
                                tagSequences.add(stringBuilder.toString());
                                stringBuilder.setLength(0);
                            }
                            if (index <= sentenceLength - 1) {
                                stringBuilder.append(" ");
                            }
                        } else {
                            String token = tokens.get(index);
                            if (token.contains(",")) {
                                token = tokenizer.removeCommaAndDot(token);
                            }
                            if (ConstantWordsCache.constantWordsCacheMap.containsKey(tokenizer.decapitalize(token))) {
                                stringBuilder.append(ConstantWordsCache.constantWordsCacheMap.get(tokenizer.decapitalize(token)));
                                if (commaIndexes.contains(index)) {
                                    tagSequences.add(stringBuilder.toString());
                                    stringBuilder.setLength(0);
                                }
                                if (index <= sentenceLength - 1) {
                                    stringBuilder.append(" ");
                                }
                            } else {
                                stringBuilder.append(TagsConversionCache.cache.get(tag));
                                if (commaIndexes.contains(index)) {
                                    tagSequences.add(stringBuilder.toString());
                                    stringBuilder.setLength(0);
                                }
                                if (index <= sentenceLength - 1) {
                                    stringBuilder.append(" ");
                                }
                            }

                        }
                    }
                    index++;
                }
            }
            tagSequences.add(stringBuilder.toString());
        }
        return tagSequences;
    }
}
