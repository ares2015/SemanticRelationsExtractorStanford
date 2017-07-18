package com.semanticRelationsExtractorStanford.tagger;

import org.junit.Test;

import java.util.List;

/**
 * Created by Oliver on 7/18/2017.
 */
public class PosTaggerTest {

    private PosTagger posTagger = new PosTagger();

    @Test
    public void test() {
        List<String> tagsSequences = posTagger.tag("in the west, however, French troops rapidly overran Lorraine, and peace was restored as early as 1735");
//        assertTrue(tagsSequences.contains("PR DET N"));
//        assertTrue(tagsSequences.contains(" WAV"));
//        assertTrue(tagsSequences.contains("N N AV Ved N"));
//        assertTrue(tagsSequences.contains(" AO N IA Ved PR AV PR NR"));
        for (String tagSequence : tagsSequences) {
            System.out.println(tagSequence);
        }
        tagsSequences.clear();
        tagsSequences = posTagger.tag("boy eats cookies");
//        Assert.assertTrue(tagsSequences.contains("N V N"));
        for (String tagSequence : tagsSequences) {
            System.out.println(tagSequence);
        }
    }
}
