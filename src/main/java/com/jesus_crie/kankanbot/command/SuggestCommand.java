package com.jesus_crie.kankanbot.command;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jesus_crie.kankanbot.util.CommandUtils;
import com.jesus_crie.kankanbot.util.EmbedBuilderCustom;
import com.jesus_crie.kankanbot.util.MessageUtils;
import net.dv8tion.jda.core.entities.Message;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static java.awt.Color.GREEN;

public class SuggestCommand extends Command {

    public SuggestCommand() {
        super("suggest",
                "Make some suggestions to my creator to improve me.",
                CommandUtils.COMMAND_PREFIX + "suggest <suggestion>",
                AccessLevel.EVERYONE,
                false);
    }

    @Override
    public boolean isValid(Message msg, String[] args) {
        return true;
    }

    @Override
    public void execute(Message msg, String[] args) {
        msg.getChannel().sendTyping();
        try {
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, String> suggestions = mapper.readValue(new File("./suggestion.json"), new TypeReference<List<String>>() {});

            if (args.length >= 1) {
                String suggest = String.join(" ", args);
                suggestions.put(msg.getAuthor().getName() + "#" + msg.getAuthor().getDiscriminator(), suggest);

                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                mapper.writeValue(new File("./suggestion.json"), suggestions);

                EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
                builder.setColor(GREEN);
                builder.setTitle("Thanks for your suggestion !");
                builder.setDescription("**Added:** " + suggest);

                msg.getChannel().sendMessage(builder.build()).queue();
            } else {
                EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
                builder.setColor(GREEN);
                builder.setTitle("Suggestions:");

                StringBuilder sb = new StringBuilder();
                suggestions.forEach((u, s) -> sb.append("- " + u + ": " + s + "\n"));
                builder.setDescription(sb.toString());

                msg.getChannel().sendMessage(builder.build()).queue();
            }
        } catch (IOException e) {
            e.printStackTrace();
            msg.getChannel().sendMessage(MessageUtils.getErrorMessage("IOException, please contact an admin.", msg.getAuthor())).queue();
        }
    }
}
