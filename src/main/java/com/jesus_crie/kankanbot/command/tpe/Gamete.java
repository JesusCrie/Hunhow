package com.jesus_crie.kankanbot.command.tpe;

import java.util.Arrays;

public class Gamete {

    private String geneM;
    private String geneV;

    public Gamete(GeneM m, GeneV v) {
        geneM = m.toString();
        geneV = v.toString();
    }

    private GeneM getMain() {
        return GeneM.parse(geneM);
    }

    private GeneV getSecond() {
        return GeneV.parse(geneV);
    }

    public Genotype add(Gamete other) {
        return new Genotype(
                Arrays.asList(getMain(), other.getMain()),
                Arrays.asList(getSecond(), other.getSecond())
        );
    }
}
