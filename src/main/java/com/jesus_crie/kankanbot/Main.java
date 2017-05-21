package com.jesus_crie.kankanbot;

public class Main {

    private static HunhowBot bot;

    public static void main(String[] args) {
        bot = new HunhowBot("pas de token");
    }

    public static void restart() {
        System.out.println("\n////////////////////////////////");
        System.out.println("////////// RESTARTING //////////");
        System.out.println("////////////////////////////////\n");
        try {
            bot.getMusicManager().disconnectFromAll();
            bot.shutdown(false);
            Thread.sleep(3000);
            bot = new HunhowBot("pas de token");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
