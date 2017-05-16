package com.jesus_crie.kankanbot;

public class Main {

    private static HunhowBot bot;

    public static void main(String[] args) {
        bot = new HunhowBot("MjE4NzQyOTkxNzQ4MDcxNDI0.Cz3eWQ.UQH1g5F4AsyvPSoqO8dPS-woPYQ");
    }

    public static void restart() {
        System.out.println("\n////////////////////////////////");
        System.out.println("////////// RESTARTING //////////");
        System.out.println("////////////////////////////////\n");
        try {
            bot.getMusicManager().disconnectFromAll();
            bot.shutdown(false);
            Thread.sleep(3000);
            bot = new HunhowBot("MjE4NzQyOTkxNzQ4MDcxNDI0.Cz3eWQ.UQH1g5F4AsyvPSoqO8dPS-woPYQ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
