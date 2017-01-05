package com.jesus_crie.kankanbot.command;

import com.jesus_crie.kankanbot.command.tpe.Character;
import com.jesus_crie.kankanbot.command.tpe.GeneM;
import com.jesus_crie.kankanbot.command.tpe.GeneV;
import com.jesus_crie.kankanbot.command.tpe.Genotype;
import com.jesus_crie.kankanbot.util.CommandUtils;
import com.jesus_crie.kankanbot.util.EmbedBuilderCustom;
import com.jesus_crie.kankanbot.util.MessageUtils;
import net.dv8tion.jda.core.entities.Message;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class TpeCommand extends Command {

    public TpeCommand() {
        super("tpe",
                "This is a personnal command for our TPE.\nIt calculate the probabilities of your child to have this or this eye color.",
                CommandUtils.COMMAND_PREFIX + "tpe <geno1> <geno2>",
                AccessLevel.MODO,
                true);
    }

    @Override
    public boolean isValid(Message msg, String[] args) {
        if (args.length >= 2 && args[0].length() == 4 && args[1].length() == 4) {
            return true;
        }
        return false;
    }

    @Override
    public void execute(Message msg, String[] args) {
        Character char1 = new Character(
                new Genotype(
                        Arrays.asList(
                                GeneM.parse(args[0].charAt(0)),
                                GeneM.parse(args[0].charAt(1))
                        ),
                        Arrays.asList(
                                GeneV.parse(args[0].charAt(2)),
                                GeneV.parse(args[0].charAt(3))
                        )
                )
        );

        Character char2 = new Character(
                new Genotype(
                        Arrays.asList(
                                GeneM.parse(args[1].charAt(0)),
                                GeneM.parse(args[1].charAt(1))
                        ),
                        Arrays.asList(
                                GeneV.parse(args[1].charAt(2)),
                                GeneV.parse(args[1].charAt(3))
                        )
                )
        );

        calculate(char1, char2, msg);
    }

    private void calculate(Character c1, Character c2, Message msg) {
        List<Character> result = Arrays.asList(
                new Character(c1.getGamete()[0].add(c2.getGamete()[0])),
                new Character(c1.getGamete()[0].add(c2.getGamete()[1])),
                new Character(c1.getGamete()[0].add(c2.getGamete()[2])),
                new Character(c1.getGamete()[0].add(c2.getGamete()[3])),

                new Character(c1.getGamete()[1].add(c2.getGamete()[0])),
                new Character(c1.getGamete()[1].add(c2.getGamete()[1])),
                new Character(c1.getGamete()[1].add(c2.getGamete()[2])),
                new Character(c1.getGamete()[1].add(c2.getGamete()[3])),

                new Character(c1.getGamete()[2].add(c2.getGamete()[0])),
                new Character(c1.getGamete()[2].add(c2.getGamete()[1])),
                new Character(c1.getGamete()[2].add(c2.getGamete()[2])),
                new Character(c1.getGamete()[2].add(c2.getGamete()[3])),

                new Character(c1.getGamete()[3].add(c2.getGamete()[0])),
                new Character(c1.getGamete()[3].add(c2.getGamete()[1])),
                new Character(c1.getGamete()[3].add(c2.getGamete()[2])),
                new Character(c1.getGamete()[3].add(c2.getGamete()[3]))
        );

        int probaBrown = 0;
        int probaGreen = 0;
        int probaBlue = 0;

        for (Character ch : result) {
            switch (ch.getType()) {
                default:
                    msg.getChannel().sendMessage(MessageUtils.getErrorMessage("ERROR while calculating probabilities !", msg.getAuthor())).queue();
                    return;
                case BROWN:
                    probaBrown++;
                    break;
                case GREEN:
                    probaGreen++;
                    break;
                case BLUE:
                    probaBlue++;
                    break;
            }
        }

        if ((probaBlue + probaGreen + probaBrown) != 16) {
            msg.getChannel().sendMessage(MessageUtils.getErrorMessage("ERROR in probabilities !", msg.getAuthor())).queue();
            return;
        }

        EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
        builder.setColor(Color.GREEN);
        builder.setTitle("TPE calculator");
        builder.setDescription("Probability  BROWN: **" + (probaBrown * 6.25) + "%**\n" +
                "Probability GREEN: **" + (probaGreen * 6.25) + "%**\n" +
                "Probability BLUE: **" + (probaBlue * 6.25) + "%**");
        msg.getChannel().sendMessage(builder.build()).queue();
    }
}
