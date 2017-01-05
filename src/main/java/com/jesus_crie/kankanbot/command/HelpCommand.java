package com.jesus_crie.kankanbot.command;

import com.jesus_crie.kankanbot.Main;
import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;
import com.jesus_crie.kankanbot.util.CommandUtils;
import com.jesus_crie.kankanbot.util.EmbedBuilderCustom;
import com.jesus_crie.kankanbot.util.MessageUtils;
import net.dv8tion.jda.core.entities.Message;

import java.awt.*;
import java.util.List;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("help",
                "Show all commands available and help for a specific command.",
                CommandUtils.COMMAND_PREFIX + "help [page|command]",
                AccessLevel.EVERYONE,
                false);
    }

    @Override
    public boolean isValid(Message msg, String[] args) {
        return true;
    }

    @Override
    public void execute(Message msg, String[] args) {
        Logger.info("Executing command help", LogFrom.COMMAND);

        if (args.length <= 0) {
            showHelp(msg, 0);
            return;
        }

        try {
            int res = Integer.parseInt(args[0]);
            showHelp(msg, res - 1);
        } catch (NumberFormatException e) {
            showCommandHelp(msg, args[0].toLowerCase());
        }
    }

    private void showHelp(Message msg, int page) {
        if (page > (int) (Math.ceil(CommandManager.getCommands().size()) / 3) || page < 0) {
            msg.getChannel().sendMessage(MessageUtils.getErrorMessage("Sorry this page doesn't exist !", msg.getAuthor())).queue();
            return;
        }

        EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
        builder.setTitle("Available commands (" + (CommandManager.getCommands().size() - CommandManager.getHiddens()) + ")");
        builder.setColor(Color.WHITE);

        List<Command> cmds = CommandManager.getCommands();
        builder.setDescription("Page " + (page + 1) + " of " + (int) Math.ceil(cmds.size() / 3));
        for (int i = (3 * page); i <= (3 * page + 2); i++) {
            if (i >= cmds.size())
                continue;
            Command c = cmds.get(i);

            if (c != null) {
                StringBuilder descBuilder = new StringBuilder();
                descBuilder.append("Access Level: **" + c.getAccessLevel().toString() + "**\n\n");
                descBuilder.append(c.getDescription());
                descBuilder.append("\n\n");

                if (c.hasSubCommands()) {
                    descBuilder.append("**Sub commands:**\n");
                    for (SubCommand s : c.getSubCommands())
                        descBuilder.append("`" + s.getSyntax() + "`\n");
                    descBuilder.append("\n\n");
                }

                if (c.hasAliases()) {
                    descBuilder.append("Aliases: *");
                    for (String al : c.getAliases()) {
                        descBuilder.append(al);
                        if (c.getAliases().indexOf(al) != c.getAliases().size() - 1)
                            descBuilder.append(", ");
                    }
                    descBuilder.append("*\n\n");
                }
                descBuilder.append("To get more informations use `" + CommandUtils.COMMAND_PREFIX + "help " + c.getName() + "`.");

                builder.addField(CommandUtils.COMMAND_PREFIX + c.getName(), descBuilder.toString(), true);
            }
        }

        msg.getChannel().sendMessage(builder.build()).queue();
    }

    private void showCommandHelp(Message msg, String cmd) {
        Command c = CommandManager.getCommand(cmd);
        if (c == null) {
            msg.getChannel().sendMessage(MessageUtils.getErrorMessage("Sorry, that command doesn't exist !\n" +
                    "Use `" + CommandUtils.COMMAND_PREFIX + "help` for the list of all available commands.", msg.getAuthor())).queue();
            return;
        }

        EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
        builder.setColor(Color.WHITE);
        builder.setTitle("Help for command **" + cmd + "**");
        builder.setDescription(c.getDescription() + "\n\n" +
                "Syntax: `" + c.getSyntax() + "`\n\n" +
                "Access Level: **" + c.getAccessLevel().toString() + "**");

        if (c.hasSubCommands()) {
            for (SubCommand sub : c.getSubCommands())
                builder.addField(sub.getName(),
                        sub.getDescription() +
                            "\n\nSyntax: `" + sub.getSyntax() + "`",
                        true);
        }

        msg.getChannel().sendMessage(builder.build()).queue();
    }
}
