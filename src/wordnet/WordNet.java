package wordnet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class WordNet {

    private final SAP sap;
    private final Map<Integer, String> idToSynset = new HashMap<>();
    private final Map<String, Set<Integer>> nounToIds = new HashMap<>();

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        sap = new SAP(initHypernyms(hypernyms));
        initSynsets(synsets);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounToIds.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return !(null == word || "".equals(word)) && nounToIds.keySet().contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        return sap.length(nounToIds.get(nounA), nounToIds.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        return idToSynset.get(sap.ancestor(nounToIds.get(nounA), nounToIds.get(nounB)));
    }

    private void initSynsets(String synsetFile) {
        In file = new In(synsetFile);
        while (file.hasNextLine()) {
            String[] line = file.readLine().split(",");
            Integer synsetId = Integer.valueOf(line[0]);
            String synset = line[1];
            idToSynset.put(synsetId, synset);

            String[] nounsFromSynset = synset.split(" ");
            for (String noun : nounsFromSynset) {
                Set<Integer> ids = nounToIds.get(noun);
                if (null == ids) {
                    ids = new HashSet<>();
                }
                ids.add(synsetId);
                nounToIds.put(noun, ids);
            }
        }
    }

    private Digraph initHypernyms(String hypernyms) {
        Digraph graph = new Digraph(idToSynset.size());
        In file = new In(hypernyms);
        while (file.hasNextLine()) {
            String[] line = file.readLine().split(",");
            Integer synsetId = Integer.valueOf(line[0]);
            for (int i = 1; i < line.length; i++) {
                Integer id = Integer.valueOf(line[i]);
                graph.addEdge(synsetId, id);
            }
        }
        return graph;
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}