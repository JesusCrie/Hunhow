package com.jesus_crie.kankanbot.command;

import com.jesus_crie.kankanbot.util.CommandUtils;
import com.jesus_crie.kankanbot.util.EmbedBuilderCustom;
import com.jesus_crie.kankanbot.util.MessageUtils;
import com.jesus_crie.kankanbot.warframe.Warframe;
import net.dv8tion.jda.core.entities.Message;

import java.awt.*;
import java.util.Collections;

public class WarframeCommand extends Command {

    public WarframeCommand() {
        super("warframe",
                Collections.singletonList("wf"),
                "Manage the Warframe component.",
                CommandUtils.COMMAND_PREFIX + "warframe [component] [on|off]",
                AccessLevel.GODTIER,
                false);

        super.registerSubCommands(
                new Alert(),
                new Sortie(),
                new Refresh()
        );
    }

    @Override
    public boolean isValid(Message msg, String[] args) {
        return true;
    }

    @Override
    public void execute(Message msg, String[] args) {
        if (args.length <= 0) {
            EmbedBuilderCustom builer = new EmbedBuilderCustom(msg.getAuthor());
            builer.setColor(Color.CYAN);
            builer.setTitle("Warframe component status");

            StringBuilder sb = new StringBuilder();
            sb.append("- Alert Loop Status: ");
            if (Warframe.getInstance().getStatusAlert())
                sb.append("**ON**\n");
            else
                sb.append("**OFF**\n");
            sb.append("- Sortie Loop Status: ");
            if (Warframe.getInstance().getStatusSortie())
                sb.append("**ON**\n");
            else
                sb.append("**OFF**\n");
            builer.setDescription(sb.toString());

            msg.getChannel().sendMessage(builer.build()).queue();
            return;
        }

        SubCommand command = super.getSubCommand(args[0]);

        if (command != null)
            if (command.isValid(msg, super.getSubArgs(args)))
                command.execute(msg, super.getSubArgs(args), null);
            else
                msg.getChannel().sendMessage(MessageUtils.getErrorMessage("Invalid syntax !\nUsage: `" + command.getSyntax() + "`", msg.getAuthor()));
        else
            msg.getChannel().sendMessage(
                    MessageUtils.getErrorMessage("Unknow subcommand, use `" + CommandUtils.COMMAND_PREFIX + "help warframe` to get help.", msg.getAuthor())).queue();
    }

    private static class Alert extends SubCommand {

        public Alert() {
            super("alert",
                    "Access the alert management component.",
                    CommandUtils.COMMAND_PREFIX + "warframe alert [on|off]");
        }

        @Override
        public boolean isValid(Message msg, String[] args) {
            return true;
        }

        @Override
        public void execute(Message msg, String[] args, Object param) {
            if (args.length <= 0) {
                EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
                builder.setColor(Color.CYAN);
                builder.setTitle("Warframe Alert Component Status");

                StringBuilder sb = new StringBuilder();
                sb.append("Status: ");
                if (Warframe.getInstance().getStatusAlert()) {
                    sb.append("**ON**\n");
                    sb.append("Current alerts: **" + Warframe.getInstance().getWorldState().getAlerts().size() + "**\n");
                } else
                    sb.append("**OFF**\nEnable to see more.");
                builder.setDescription(sb.toString());

                msg.getChannel().sendMessage(builder.build()).queue();
                return;
            }
            switch (args[0].toLowerCase()) {
                case "on":
                    Warframe.getInstance().startAlertLoop();
                    EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
                    builder.setColor(Color.CYAN);
                    builder.setTitle("Warframe Alert Component successfully turned ON !");
                    msg.getChannel().sendMessage(builder.build()).queue();
                    break;
                case "off":
                    Warframe.getInstance().endAlertLoop();
                    EmbedBuilderCustom builder2 = new EmbedBuilderCustom(msg.getAuthor());
                    builder2.setColor(Color.CYAN);
                    builder2.setTitle("Warframe Alert Component successfully turned OFF !");
                    msg.getChannel().sendMessage(builder2.build()).queue();
                    break;
                default:
                    msg.getChannel().sendMessage(MessageUtils.getErrorMessage("Unknow state, use `on` or `off`.", msg.getAuthor())).queue();
                    break;
            }
        }
    }

    /*private static class Invasion {

    }*/

    private static class Sortie extends SubCommand {

        public Sortie() {
            super("sortie",
                    "Access the sortie management component.",
                    CommandUtils.COMMAND_PREFIX + "warframe sortie [on|off]");
        }

        @Override
        public boolean isValid(Message msg, String[] args) {
            return true;
        }

        @Override
        public void execute(Message msg, String[] args, Object param) {
            if (args.length <= 0) {
                EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
                builder.setColor(Color.CYAN);
                builder.setTitle("Warframe Sortie Component Status");

                StringBuilder sb = new StringBuilder();
                sb.append("Status: ");
                if (Warframe.getInstance().getStatusSortie()) {
                    sb.append("**ON**\n");
                } else
                    sb.append("**OFF**\n");
                builder.setDescription(sb.toString());

                msg.getChannel().sendMessage(builder.build()).queue();
                return;
            }
            switch (args[0].toLowerCase()) {
                case "on":
                    Warframe.getInstance().startSortieLoop();
                    EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
                    builder.setColor(Color.CYAN);
                    builder.setTitle("Warframe Sortie Component successfully turned ON !");
                    msg.getChannel().sendMessage(builder.build()).queue();
                    break;
                case "off":
                    Warframe.getInstance().endSortieLoop();
                    EmbedBuilderCustom builder2 = new EmbedBuilderCustom(msg.getAuthor());
                    builder2.setColor(Color.CYAN);
                    builder2.setTitle("Warframe Sortie Component successfully turned OFF !");
                    msg.getChannel().sendMessage(builder2.build()).queue();
                    break;
                default:
                    msg.getChannel().sendMessage(MessageUtils.getErrorMessage("Unknow state, use `on` or `off`.", msg.getAuthor())).queue();
                    break;
            }
        }
    }

    public static class Refresh extends SubCommand {

        public Refresh() {
            super("refresh",
                    "Refresh the names and nodes database.",
                    CommandUtils.COMMAND_PREFIX + "warframe refresh");
        }

        @Override
        public boolean isValid(Message msg, String[] args) {
            return true;
        }

        @Override
        public void execute(Message msg, String[] args, Object param) {
            msg.getChannel().sendTyping();
            Warframe.getInstance().reloadNames();

            EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
            builder.setColor(Color.CYAN);
            builder.setTitle("Names and nodes successfully reloaded !");

            msg.getChannel().sendMessage(builder.build()).queue();
        }
    }
}
