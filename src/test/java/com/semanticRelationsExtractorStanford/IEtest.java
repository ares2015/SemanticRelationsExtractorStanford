package com.semanticRelationsExtractorStanford;


import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;

/**
 * Created by Oliver on 9/30/2017.
 */
public class IEtest {
    public static void main(String[] args) throws Exception {
        // Create a CoreNLP document
        Document doc = new Document("Sartre saw Christ as part of the Jewish Resistance to the Roman Empire's occupation mirroring the French Resistance of Nazi Germany's .");

        // Iterate over the sentences in the document
        for (Sentence sent : doc.sentences()) {
            // Iterate over the triples in the sentence
            for (RelationTriple triple : sent.openieTriples()) {
                // Print the triple
                System.out.println(triple.confidence + "\t" +
                        triple.subject + "\t" +
                        triple.relation + "\t" +
                        triple.object);
            }
        }
    }
}
