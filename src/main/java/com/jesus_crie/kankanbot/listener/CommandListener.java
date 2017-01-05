package com.jesus_crie.kankanbot.listener;

import com.jesus_crie.kankanbot.command.Command;
import com.jesus_crie.kankanbot.command.CommandManager;
import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;
import com.jesus_crie.kankanbot.permission.Permissions;
import com.jesus_crie.kankanbot.util.CommandUtils;
import com.jesus_crie.kankanbot.util.MessageUtils;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.apache.commons.lang3.StringUtils;

public class CommandListener extends ListenerAdapter {

    public CommandListener() {
        Logger.info("  - Command Listener successfuly registered", LogFrom.LISTENER);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getAuthor().getId().equalsIgnoreCase(e.getJDA().getSelfUser().getId()) || e.getChannelType() != ChannelType.TEXT)
            return;

        final Message m = e.getMessage();

        if (m.getContent() != null && m.getContent().startsWith(CommandUtils.COMMAND_PREFIX)) {
            final TextChannel channel = (TextChannel) m.getChannel();

            Logger.info(m.getAuthor().getName() + " issued a command from " + m.getGuild().getName() + ": \"" + m.getContent() + "\"", LogFrom.LISTENER);

            final String content = m.getRawContent().substring(1);
            new Thread(() -> {
                m.deleteMessage().queue();

                String cmdName = content.split(" ")[0];
                String[] args = StringUtils.split(content.substring(cmdName.length()).trim(), " ");

                Command cmd = CommandManager.getCommand(cmdName);

                if (cmd != null) {
                    if (Permissions.getUserAccess(m.getGuild().getMember(m.getAuthor())).isSuperiorThan(cmd.getAccessLevel()))
                        if (cmd.isValid(m, args))
                            cmd.execute(m, args);
                        else
                            channel.sendMessage(MessageUtils.getErrorMessage("Invalid syntax.\nUse `" + CommandUtils.COMMAND_PREFIX + "help " + cmd.getName() + "`", m.getAuthor())).queue();
                    else
                        channel.sendMessage(MessageUtils.getErrorMessage("Sorry, you don't have the permission for this command", m.getAuthor())).queue();
                } else
                    channel.sendMessage(MessageUtils.getErrorMessage("Unknow command, use `" + CommandUtils.COMMAND_PREFIX + "help` to see available commands.", m.getAuthor())).queue();
            }).start();
        }
    }
}
