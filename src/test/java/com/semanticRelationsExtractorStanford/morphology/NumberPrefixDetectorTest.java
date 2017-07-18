package com.semanticRelationsExtractorStanford.morphology;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Oliver on 7/18/2017.
 */
public class NumberPrefixDetectorTest {

    @Test
    public void test() {
        NumberPrefixDetector numberPrefixDetector = new NumberPrefixDetectorImpl();
        assertTrue(numberPrefixDetector.detect("1735"));
    }
}
