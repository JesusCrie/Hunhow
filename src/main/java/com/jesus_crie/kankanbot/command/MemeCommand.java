package com.jesus_crie.kankanbot.command;

import com.jesus_crie.kankanbot.util.CommandUtils;
import com.jesus_crie.kankanbot.util.EmbedBuilderCustom;
import com.jesus_crie.kankanbot.util.MessageUtils;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;
import java.util.*;
import java.util.List;

public class MemeCommand extends Command {

    public MemeCommand() {
        super("meme",
                Collections.singletonList("me"),
                "Use to send random meme or meme on a theme like salt. Very usefull for trollers.",
                CommandUtils.COMMAND_PREFIX + "meme [theme]",
                AccessLevel.EVERYONE,
                false);
        super.registerSubCommands(
                new Salt(),
                new Rajeu(),
                new Doge(),
                new Shutup(),
                new Erno(),
                new Troll()
        );
    }

    @Override
    public boolean isValid(Message msg, String[] args) {
        if (args.length <= 0)
            return true;
        return (super.getSubCommand(args[0]).isValid(msg, args));
    }

    @Override
    public void execute(Message msg, String[] args) {

        if (!(args.length <= 0)) {
            SubCommand command = super.getSubCommand(args[0]);
            if (command != null)
                if (command.isValid(msg, super.getSubArgs(args)))
                    command.execute(msg, super.getSubArgs(args), null);
                else
                    msg.getChannel().sendMessage(MessageUtils.getErrorMessage("Invalid syntax.\nUsage: `" + command.getSyntax() + "`", msg.getAuthor())).queue();
            else
                msg.getChannel().sendMessage(MessageUtils.getErrorMessage("Unknow subcommand use `" + CommandUtils.COMMAND_PREFIX + "help music` to show some help", msg.getAuthor())).queue();
        } else {
            SubCommand sub = super.getSubCommands().get(new Random().nextInt(super.getSubCommands().size()));
            if (sub.isValid(msg, new String[0]))
                sub.execute(msg, new String[0], null);
            else
                msg.getChannel().sendMessage(MessageUtils.getErrorMessage("An error occured while validating a meme command.", msg.getAuthor())).queue();
        }
    }

    private static MessageEmbed createMemeMessage(String title, String imageUrl, Message msg) {
        EmbedBuilderCustom builder = new EmbedBuilderCustom(msg.getAuthor());
        builder.setColor(new Color(0, 255, 255));
        builder.setTitle(title);
        builder.setImage(imageUrl);

        return builder.build();
    }

    private static class Salt extends SubCommand {

        private static HashMap<String, String> memes = new HashMap<>();
        static {
            HashMap<String, String> temp = new HashMap<>();
            temp.put("**MY FRIES COULD USE YOUR SAAAALT**", "http://www.jesus-crie.com/meme/salt_fries.jpg");
            temp.put("**THERE SO MUCH SAAAALT**", "http://www.jesus-crie.com/meme/salt_granny.jpg");
            temp.put("**SALT EVERYWHERE**", "http://www.jesus-crie.com/meme/salt_buzz.jpg");
            temp.put("**SALT IS WITH YOU**", "http://www.jesus-crie.com/meme/salt_vador.jpg");
            memes = temp;
            temp = null;
        }

        public Salt() {
            super("salt",
                    "Send a random *saaaalt* meme !",
                    CommandUtils.COMMAND_PREFIX + "meme salt [index]");
        }

        @Override
        public boolean isValid(Message msg, String[] args) {
            return true;
        }

        @Override
        public void execute(Message msg, String[] args, Object param) {
            if (args.length <= 0) {
                List<String> keys = new ArrayList<>(memes.keySet());
                String randKey = keys.get(new Random().nextInt(keys.size()));

                msg.getChannel().sendMessage(MemeCommand.createMemeMessage(randKey, memes.get(randKey), msg)).queue();
            } else {
                try {
                    int i = Integer.parseInt(args[0]);
                    String key = new ArrayList<>(memes.keySet()).get(i);
                    if (i >= 0 && i < memes.keySet().size())
                        msg.getChannel().sendMessage(MemeCommand.createMemeMessage(key, memes.get(key), msg)).queue();
                    else
                        msg.getChannel().sendMessage(MessageUtils.getErrorMessage("Indefined index !", msg.getAuthor())).queue();
                } catch (NumberFormatException e) {}
            }
        }
    }

    private static class Rajeu extends SubCommand {

        private static HashMap<String, String> memes = new HashMap<>();
        static {
            HashMap<String, String> temp = new HashMap<>();
            temp.put("**REGARDE, UN RAGEU !**", "http://www.jesus-crie.com/meme/rajeu_buzz.jpg");
            temp.put("**ALALA J'ENTEND PAS LES RAGEUX**", "http://www.jesus-crie.com/meme/rajeu_rebeu.jpg");
            temp.put("**J'ENTEND RIEN**", "http://www.jesus-crie.com/meme/rajeu_obama.jpg");
            temp.put("**RAAAAGEUUUUX**", "http://www.jesus-crie.com/meme/rajeu_bob.jpg");
            memes = temp;
            temp = null;
        }

        public Rajeu() {
            super("rajeu",
                    "Send a random meme of rageux !",
                    CommandUtils.COMMAND_PREFIX + "meme rajeu [index]");
        }

        @Override
        public boolean isValid(Message msg, String[] args) {
            return true;
        }

        @Override
        public void execute(Message msg, String[] args, Object param) {
            if (args.length <= 0) {
                List<String> keys = new ArrayList<>(memes.keySet());
                String randKey = keys.get(new Random().nextInt(keys.size()));

                msg.getChannel().sendMessage(MemeCommand.createMemeMessage(randKey, memes.get(randKey), msg)).queue();
            } else {
                try {
                    int i = Integer.parseInt(args[0]);
                    String key = new ArrayList<>(memes.keySet()).get(i);
                    if (i >= 0 && i < memes.keySet().size())
                        msg.getChannel().sendMessage(MemeCommand.createMemeMessage(key, memes.get(key), msg)).queue();
                    else
                        msg.getChannel().sendMessage(MessageUtils.getErrorMessage("Indefined index !", msg.getAuthor())).queue();
                } catch (NumberFormatException e) {}
            }
        }
    }

    private static class Doge extends SubCommand {

        private static HashMap<String, String> memes = new HashMap<>();
        static {
            HashMap<String, String> temp = new HashMap<>();
            temp.put("**SO MUCH DOGE, THEO MANGE LE PAS !**", "http://www.jesus-crie.com/meme/doge_chin.jpg");
            temp.put("**SUCH WOW, AWESOME**", "http://www.jesus-crie.com/meme/doge_wow2.jpg");
            temp.put("**SUCH RAINBOW, WOW**", "http://www.jesus-crie.com/meme/doge_rainbow.jpg");
            temp.put("**SUCH DOGE, SUCH CEREAL**", "http://www.jesus-crie.com/meme/doge_wow.jpg");
            memes = temp;
            temp = null;
        }

        public Doge() {
            super("doge",
                    "Send a random DOGE !",
                    CommandUtils.COMMAND_PREFIX + "meme doge [index]");
        }

        @Override
        public boolean isValid(Message msg, String[] args) {
            return true;
        }

        @Override
        public void execute(Message msg, String[] args, Object param) {
            if (args.length <= 0) {
                List<String> keys = new ArrayList<>(memes.keySet());
                String randKey = keys.get(new Random().nextInt(keys.size()));

                msg.getChannel().sendMessage(MemeCommand.createMemeMessage(randKey, memes.get(randKey), msg)).queue();
            } else {
                try {
                    int i = Integer.parseInt(args[0]);
                    String key = new ArrayList<>(memes.keySet()).get(i);
                    if (i >= 0 && i < memes.keySet().size())
                        msg.getChannel().sendMessage(MemeCommand.createMemeMessage(key, memes.get(key), msg)).queue();
                    else
                        msg.getChannel().sendMessage(MessageUtils.getErrorMessage("Indefined index !", msg.getAuthor())).queue();
                } catch (NumberFormatException e) {}
            }
        }
    }

    private static class Shutup extends SubCommand {

        private static HashMap<String, String> memes = new HashMap<>();
        static {
            HashMap<String, String> temp = new HashMap<>();
            temp.put("**CAN YOU JUST SHUT THE FUCK UP ?**", "http://www.jesus-crie.com/meme/shut_stark.jpg");
            temp.put("**SHUT THE FUCK UP !!!**", "http://www.jesus-crie.com/meme/shut_fuck.jpg");
            temp.put("**WHAT ABOUT TO SHUT UP ?**", "http://www.jesus-crie.com/meme/shut_matrix.jpg");
            temp.put("**SHUT UP AND BUY ME BREAKFAST**", "http://www.jesus-crie.com/meme/shut_trump.jpg");
            memes = temp;
            temp = null;
        }

        public Shutup() {
            super("shutup",
                    "SHUTUP, just SHUTUP !",
                    CommandUtils.COMMAND_PREFIX + "meme shutup [index]");
        }

        @Override
        public boolean isValid(Message msg, String[] args) {
            return true;
        }

        @Override
        public void execute(Message msg, String[] args, Object param) {
            if (args.length <= 0) {
                List<String> keys = new ArrayList<>(memes.keySet());
                String randKey = keys.get(new Random().nextInt(keys.size()));

                msg.getChannel().sendMessage(MemeCommand.createMemeMessage(randKey, memes.get(randKey), msg)).queue();
            } else {
                try {
                    int i = Integer.parseInt(args[0]);
                    String key = new ArrayList<>(memes.keySet()).get(i);
                    if (i >= 0 && i < memes.keySet().size())
                        msg.getChannel().sendMessage(MemeCommand.createMemeMessage(key, memes.get(key), msg)).queue();
                    else
                        msg.getChannel().sendMessage(MessageUtils.getErrorMessage("Indefined index !", msg.getAuthor())).queue();
                } catch (NumberFormatException e) {}
            }
        }
    }

    private static class Erno extends SubCommand {

        private static HashMap<String, String> memes = new HashMap<>();
        static {
            HashMap<String, String> temp = new HashMap<>();
            temp.put("**NO GOD NO, PLEASE !**", "http://www.jesus-crie.com/meme/no_godno.jpg");
            temp.put("**NO**", "http://www.jesus-crie.com/meme/no_mario.png");
            temp.put("**HOW ABOUT NO ?**", "http://www.jesus-crie.com/meme/no_bear.jpg");
            temp.put("**NO**", "http://www.jesus-crie.com/meme/no_face.jpg");
            memes = temp;
            temp = null;
        }

        public Erno() {
            super("no",
                    "NO GOD NO, PLEASE NO !",
                    CommandUtils.COMMAND_PREFIX + "meme no [index]");
        }

        @Override
        public boolean isValid(Message msg, String[] args) {
            return true;
        }

        @Override
        public void execute(Message msg, String[] args, Object param) {
            if (args.length <= 0) {
                List<String> keys = new ArrayList<>(memes.keySet());
                String randKey = keys.get(new Random().nextInt(keys.size()));

                msg.getChannel().sendMessage(MemeCommand.createMemeMessage(randKey, memes.get(randKey), msg)).queue();
            } else {
                try {
                    int i = Integer.parseInt(args[0]);
                    String key = new ArrayList<>(memes.keySet()).get(i);
                    if (i >= 0 && i < memes.keySet().size())
                        msg.getChannel().sendMessage(MemeCommand.createMemeMessage(key, memes.get(key), msg)).queue();
                    else
                        msg.getChannel().sendMessage(MessageUtils.getErrorMessage("Indefined index !", msg.getAuthor())).queue();
                } catch (NumberFormatException e) {}
            }
        }
    }

    private static class Troll extends SubCommand {

        private static HashMap<String, String> memes = new HashMap<>();
        static {
            HashMap<String, String> temp = new HashMap<>();
            temp.put("**TOO MANY TROLL**", "http://www.jesus-crie.com/meme/troll_flood.jpg");
            temp.put("**SO MUCH TROLL IN THESE EYES**", "http://www.jesus-crie.com/meme/troll_bwa.jpg");
            temp.put("**LOLOLOLOLOLOLOLOL**", "http://www.jesus-crie.com/meme/troll_lolgif.gif");
            temp.put("**TROLL FACE**", "http://www.jesus-crie.com/meme/troll_face.png");
            memes = temp;
            temp = null;
        }

        public Troll() {
            super("troll",
                    "Send some trollface.",
                    CommandUtils.COMMAND_PREFIX + "meme troll [index]");
        }

        @Override
        public boolean isValid(Message msg, String[] args) {
            return true;
        }

        @Override
        public void execute(Message msg, String[] args, Object param) {
            if (args.length <= 0) {
                List<String> keys = new ArrayList<>(memes.keySet());
                String randKey = keys.get(new Random().nextInt(keys.size()));

                msg.getChannel().sendMessage(MemeCommand.createMemeMessage(randKey, memes.get(randKey), msg)).queue();
            } else {
                try {
                    int i = Integer.parseInt(args[0]);
                    String key = new ArrayList<>(memes.keySet()).get(i);
                    if (i >= 0 && i < memes.keySet().size())
                        msg.getChannel().sendMessage(MemeCommand.createMemeMessage(key, memes.get(key), msg)).queue();
                    else
                        msg.getChannel().sendMessage(MessageUtils.getErrorMessage("Indefined index !", msg.getAuthor())).queue();
                } catch (NumberFormatException e) {}
            }
        }
    }
}
