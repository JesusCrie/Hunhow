package com.jesus_crie.kankanbot.command;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jesus_crie.kankanbot.command.rule34.R34Post;
import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;
import com.jesus_crie.kankanbot.util.CommandUtils;
import com.jesus_crie.kankanbot.util.EmbedBuilderCustom;
import com.jesus_crie.kankanbot.util.MessageUtils;
import net.dv8tion.jda.core.entities.Message;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;

public class Rule34Command extends Command {

    private XmlMapper mapper;

    public Rule34Command() {
        super("rule34",
                Arrays.asList("sex", "r34"),
                "Post some cool images from Rule34. Your channel need to have \"nsfw\" in his name.",
                CommandUtils.COMMAND_PREFIX + "rule34 <query>",
                AccessLevel.EVERYONE,
                false);
        mapper = new XmlMapper();
    }

    @Override
    public boolean isValid(Message msg, String[] args) {
        return (args.length >= 1);
    }

    @Override
    public void execute(Message msg, String[] args) {
        Logger.info("Executing command rule34", LogFrom.COMMAND);
        if (msg.getChannel().getId().equalsIgnoreCase("272405334755115008") || msg.getChannel().getName().toLowerCase().contains("nsfw")) {
            try {
                String query = String.join(" ", args);
                R34Post[] datas = mapper.readValue(new URL("http://rule34.xxx/?page=dapi&s=post&q=index&limit=20&tags=" + query), R34Post[].class);
                datas = Arrays.copyOfRange(datas, 1, datas.length);
                R34Post post;
                do {
                    post = datas[new Random().nextInt(datas.length - 1)];
                } while (post.file_url.endsWith("webm"));

                EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
                builder.setColor(new Color(255, 77, 152));
                builder.setAuthor("Some images of " + query, null, "http://rule34.xxx/favicon.ico?v=2");
                builder.setImage("http:" + post.file_url);

                msg.getChannel().sendMessage(builder.build()).queue();

            } catch (IOException e) {
                e.printStackTrace();
                Logger.error("IOException on rule34 command !", LogFrom.COMMAND);
                msg.getChannel().sendMessage(MessageUtils.getErrorMessage("IOException error, please contact my creator !", msg.getAuthor())).queue();
            }
        } else
            msg.getChannel().sendMessage(MessageUtils.getErrorMessage("This command is not allow in this channel. The channel name must contains \"nsfw\".", msg.getAuthor())).queue();
    }
}
