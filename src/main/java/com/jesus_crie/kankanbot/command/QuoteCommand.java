package com.jesus_crie.kankanbot.command;

import com.jesus_crie.kankanbot.util.CommandUtils;
import com.jesus_crie.kankanbot.util.EmbedBuilderCustom;
import com.jesus_crie.kankanbot.util.MessageUtils;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

public class QuoteCommand extends Command {

    public QuoteCommand() {
        super("quote",
                Collections.singletonList("q"),
                "Quote a message that was said earlier.",
                CommandUtils.COMMAND_PREFIX + "quote <message>",
                AccessLevel.EVERYONE,
                false);
    }

    @Override
    public boolean isValid(Message msg, String[] args) {
        return (args.length >= 1);
    }

    @Override
    public void execute(Message msg, String[] args) {
        String query = String.join(" ", args).toLowerCase();

        msg.getChannel().getHistory().retrievePast(100).queue(history -> {
            history.remove(msg);
            Message toQuote = null;
            Member author = null;

            if (history.size() <= 0) {
                msg.getChannel().sendMessage(MessageUtils.getErrorMessage("This channel is empty ?", msg.getAuthor())).queue();
                return;
            }

            for (Message m : history) {
                if (m.getRawContent().toLowerCase().contains(query)
                        || m.getContent().toLowerCase().contains(query)) {
                    toQuote = m;
                    author = m.getGuild().getMember(m.getAuthor());
                    break;
                }
            }
            if (toQuote == null) {
                msg.getChannel().sendMessage(MessageUtils.getErrorMessage("No message found or too old.", msg.getAuthor())).queue();
                return;
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Europe/Paris")));
            String time = formatter.format(Date.from(toQuote.getCreationTime().toInstant()));

            EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
            builder.setColor(author.getColor());
            builder.setAuthor("By " + author.getEffectiveName() + " - Sent on " + time, null, author.getUser().getAvatarUrl());
            builder.setDescription(toQuote.getRawContent());

            msg.getChannel().sendMessage(builder.build()).queue();
        });
    }
}
