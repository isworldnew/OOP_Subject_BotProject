package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class ParkingBot extends TelegramLongPollingBot {

    public static void main(String[] args) {


        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new ParkingBot());
            System.out.println("Succesfully started bot's session");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return "sstuParkingBot";
    }

    @Override
    public String getBotToken() {
        return "6592012453:AAE6k2CGoEpWwohjBIngbjECs6lOP1ny7_o";
    }
}
