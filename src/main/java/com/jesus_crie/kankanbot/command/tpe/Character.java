package com.jesus_crie.kankanbot.command.tpe;

public class Character {

    private Genotype geno;

    public Character(Genotype geno) {
        this.geno = geno;
    }

    public EyeColor getType() {
        return geno.getType();
    }

    public Gamete[] getGamete() {
        return (Gamete[]) geno.getGamete().toArray();
    }

    @Override
    public String toString() {
        return "Type: " + geno.getType().toString() + "\n" +
                "Gene: " + geno.getMain().get(0).toString() + geno.getMain().get(1).toString() + geno.getSecond().get(0).toString() + geno.getSecond().get(1).toString();
    }
}
