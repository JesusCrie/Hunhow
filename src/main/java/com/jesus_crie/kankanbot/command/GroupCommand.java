package com.jesus_crie.kankanbot.command;

import com.jesus_crie.kankanbot.util.CommandUtils;
import com.jesus_crie.kankanbot.util.EmbedBuilderCustom;
import com.jesus_crie.kankanbot.util.MessageUtils;
import com.jesus_crie.kankanbot.warframe.AlertRoles;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GroupCommand extends Command {

    private static List<String> rolesKlub = Arrays.asList("Warframe", "Minecraft", "DontStarve", "Overwatch");

    public GroupCommand() {
        super("group",
                Collections.singletonList("g"),
                "Add yourself to the specified group.",
                CommandUtils.COMMAND_PREFIX + "group <list|group_name>",
                AccessLevel.EVERYONE,
                false,
                Arrays.asList("275314962468175872", "219802541775519744"));

        super.registerSubCommands(
                new ListGroup(),
                new Join(),
                new Leave()
        );
    }

    @Override
    public boolean isValid(Message msg, String[] args) {
        return true;
    }

    @Override
    public void execute(Message msg, String[] args) {
        if (!super.getGuilds().contains(msg.getGuild().getId())) {
            msg.getChannel().sendMessage(MessageUtils.getErrorMessage("This command is not allowed on this server !", msg.getAuthor())).queue();
            return;
        }

        if (args.length <= 0) {
            super.getSubCommand("list").execute(msg, args, null);
            return;
        }

        SubCommand command = super.getSubCommand(args[0]);

        if (command != null)
            if (command.isValid(msg, super.getSubArgs(args)))
                command.execute(msg, super.getSubArgs(args), null);
            else
                msg.getChannel().sendMessage(MessageUtils.getErrorMessage("Invalid syntax !\nUsage: `" + command.getSyntax() + "`", msg.getAuthor())).queue();
        else
            msg.getChannel().sendMessage(
                    MessageUtils.getErrorMessage("Unknow subcommand, use `" + CommandUtils.COMMAND_PREFIX + "help group` to get some help.", msg.getAuthor())).queue();
    }

    private static class ListGroup extends SubCommand {

        public ListGroup() {
            super("list",
                    "Display the list of all joinable groups on this server.",
                    CommandUtils.COMMAND_PREFIX + "group [list]");
        }

        @Override
        public boolean isValid(Message msg, String[] args) {
            return true;
        }

        @Override
        public void execute(Message msg, String[] args, Object param) {
            EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
            builder.setColor(Color.ORANGE);
            builder.setTitle("Group list:");

            StringBuilder sb = new StringBuilder();
            switch (msg.getGuild().getId()) {
                default:
                    sb.append("None");
                    break;
                case "219802541775519744":
                    rolesKlub.forEach(r -> sb.append("- " + r + "\n"));
                    break;
                case "275314962468175872":
                    AlertRoles.getRolesAsStringList().forEach(r -> sb.append("- " + r + "\n"));
                    break;
            }
            builder.setDescription(sb.toString());

            msg.getChannel().sendMessage(builder.build()).queue();
        }
    }

    public static class Join extends SubCommand {

        public Join() {
            super("join",
                    "Use to join a group.",
                    CommandUtils.COMMAND_PREFIX + "group join <group>");
        }

        @Override
        public boolean isValid(Message msg, String[] args) {
            return (args.length >= 1);
        }

        @Override
        public void execute(Message msg, String[] args, Object param) {
            String toJoin = args[0].substring(0, 1).toUpperCase() + args[0].substring(1);
            switch (msg.getGuild().getId()) {
                default:
                    msg.getChannel().sendMessage(MessageUtils.getErrorMessage("Groups aren't allowed here !", msg.getAuthor())).queue();
                    break;
                case "219802541775519744":
                    if (rolesKlub.contains(toJoin)) {
                        Role role = msg.getGuild().getRolesByName(toJoin, true).get(0);
                        msg.getGuild().getController().addRolesToMember(
                                msg.getGuild().getMember(msg.getAuthor()),
                                role
                        ).queue();
                        EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
                        builder.setColor(Color.ORANGE);
                        builder.setTitle("You've been successfully added to group " + role.getName() + " !");

                        msg.getChannel().sendMessage(builder.build()).queue();
                    } else
                        msg.getChannel().sendMessage(MessageUtils.getErrorMessage("This group doesn't exist !", msg.getAuthor())).queue();
                    break;
                case "275314962468175872":
                    if (AlertRoles.getRolesAsStringList().contains(toJoin)) {
                        Role role = msg.getGuild().getRolesByName(toJoin, true).get(0);
                        msg.getGuild().getController().addRolesToMember(
                                msg.getGuild().getMember(msg.getAuthor()),
                                role
                        ).queue();
                        EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
                        builder.setColor(Color.ORANGE);
                        builder.setTitle("You've been successfully added to group " + role.getName() + " !");

                        msg.getChannel().sendMessage(builder.build()).queue();
                    } else
                        msg.getChannel().sendMessage(MessageUtils.getErrorMessage("This group doesn't exist !", msg.getAuthor())).queue();
                    break;
                }
            }
        }

    public static class Leave extends SubCommand {

        public Leave() {
            super("leave",
                    "Use to leave a group.",
                    CommandUtils.COMMAND_PREFIX + "group leave <group>");
        }

        @Override
        public boolean isValid(Message msg, String[] args) {
            return (args.length >= 1);
        }

        @Override
        public void execute(Message msg, String[] args, Object param) {
            String toJoin = args[0].substring(0, 1).toUpperCase() + args[0].substring(1);
            switch (msg.getGuild().getId()) {
                default:
                    msg.getChannel().sendMessage(MessageUtils.getErrorMessage("Groups aren't allowed here !", msg.getAuthor())).queue();
                    break;
                case "219802541775519744":
                    if (rolesKlub.contains(toJoin)) {
                        Role role = msg.getGuild().getRolesByName(toJoin, true).get(0);
                        if (msg.getGuild().getMember(msg.getAuthor()).getRoles().contains(role)) {
                            msg.getGuild().getController().removeRolesFromMember(
                                    msg.getGuild().getMember(msg.getAuthor()),
                                    role
                            ).queue();
                            EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
                            builder.setColor(Color.ORANGE);
                            builder.setTitle("You've been successfully removed from group " + role.getName() + " !");

                            msg.getChannel().sendMessage(builder.build()).queue();
                        } else
                            msg.getChannel().sendMessage(MessageUtils.getErrorMessage("You're not in this group !", msg.getAuthor())).queue();
                    } else
                        msg.getChannel().sendMessage(MessageUtils.getErrorMessage("This group doesn't exist !", msg.getAuthor())).queue();
                    break;
                case "275314962468175872":
                    if (AlertRoles.getRolesAsStringList().contains(toJoin)) {
                        Role role = msg.getGuild().getRolesByName(toJoin, true).get(0);
                        if (msg.getGuild().getMember(msg.getAuthor()).getRoles().contains(role)) {
                            msg.getGuild().getController().removeRolesFromMember(
                                    msg.getGuild().getMember(msg.getAuthor()),
                                    role
                            ).queue();
                            EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
                            builder.setColor(Color.ORANGE);
                            builder.setTitle("You've been successfully removed from group " + role.getName() + " !");

                            msg.getChannel().sendMessage(builder.build()).queue();
                        } else
                            msg.getChannel().sendMessage(MessageUtils.getErrorMessage("You're not in this group !", msg.getAuthor())).queue();
                    } else
                        msg.getChannel().sendMessage(MessageUtils.getErrorMessage("This group doesn't exist !", msg.getAuthor())).queue();
                    break;
            }
        }
    }
}
