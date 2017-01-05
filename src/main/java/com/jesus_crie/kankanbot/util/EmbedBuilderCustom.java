package com.jesus_crie.kankanbot.util;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.User;

public class EmbedBuilderCustom extends EmbedBuilder {

    public EmbedBuilderCustom(User user) {
        super();
        super.setFooter("Query made by " + user.getName(), user.getEffectiveAvatarUrl());
    }
}
