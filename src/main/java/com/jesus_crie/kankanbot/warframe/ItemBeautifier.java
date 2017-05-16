package com.jesus_crie.kankanbot.warframe;

public class ItemBeautifier {

    public static String get(String in) {
        String[] splited = in.split("/");
        return NameMiddleware.get(splited[splited.length - 1].replaceAll("(\\p{Ll})(\\p{Lu})","$1 $2"));
    }
}
