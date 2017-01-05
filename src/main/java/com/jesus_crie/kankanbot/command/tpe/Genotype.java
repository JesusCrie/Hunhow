package com.jesus_crie.kankanbot.command.tpe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Genotype {

    private List<GeneM> main = new ArrayList<>();
    private List<GeneV> second = new ArrayList<>();

    public Genotype(List<GeneM> m, List<GeneV> s) {
        main = m;
        second = s;
    }

    public EyeColor getType() {
        if (main.contains(GeneM.M)) {
            return EyeColor.BROWN;
        } else if (second.contains(GeneV.V)) {
            return EyeColor.GREEN;
        } else {
            return EyeColor.BLUE;
        }
    }

    public List<Gamete> getGamete() {
        return Arrays.asList(
                new Gamete(main.get(0), second.get(0)),
                new Gamete(main.get(0), second.get(1)),
                new Gamete(main.get(1), second.get(0)),
                new Gamete(main.get(1), second.get(1))
        );
    }

    public List<GeneM> getMain() {
        return main;
    }

    public List<GeneV> getSecond() {
        return second;
    }
}
