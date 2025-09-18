package chatbot.java;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class CVFrogBot extends TelegramLongPollingBot 
{
    private static final Logger LOGGER = Logger.getLogger(CVFrogBot.class.getName());
    private final Responder responseGenerator;
    
    public CVFrogBot() 
    { 
        responseGenerator = new Responder();
    }

    @Override
    public String getBotUsername() 
    {
        return ("CVFrogBot");
    }
    
    @Override
    public String getBotToken() 
    {
        return ("BOT_TOKEN");
    }    
    
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            handleButtonPress(update);
        } else if (update.hasMessage() && update.getMessage().hasText()) {
            handleTextMessage(update);
        }
    }

    private void handleButtonPress(Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        String callbackData = update.getCallbackQuery().getData();
        
        try {
            int choice = Integer.parseInt(callbackData);

                if(choice!=0)
                {
                    String response = responseGenerator.generateResponse(choice);
                    
                    SendMessage message = new SendMessage();
                    message.setChatId(String.valueOf(chatId));
                    message.setText(response);
                    
                    // Verifica se precisa adicionar sub-botões
                    InlineKeyboardMarkup keyboard = createSubMenuKeyboard(choice);
                    if (keyboard != null) {
                        message.setReplyMarkup(keyboard);
                    } else if (choice != 0) { // Se não for menu principal nem submenu, adiciona botão Voltar
                        message.setReplyMarkup(createBackButton());
                    }
                    
                    execute(message);
                }
            else 
             sendMainMenu(chatId);
        } catch (NumberFormatException | TelegramApiException e) {
            sendErrorMessage(chatId);
        }
    }
    private InlineKeyboardMarkup createSubMenuKeyboard(int choice) {
        return switch (choice) 
        {
            // Se tiver adicionar cases
            default -> null; // Menu principal ou outros
        };
    }

    private InlineKeyboardMarkup createStoreSubMenu() {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        // Botão para Voltar
        InlineKeyboardButton backButton = new InlineKeyboardButton();
        backButton.setText("Voltar");
        backButton.setCallbackData("0");
        
        keyboard.add(List.of(backButton));
        
        return new InlineKeyboardMarkup(keyboard);
    }
    
    private InlineKeyboardMarkup createGamesSubMenu() {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        // Botão para Voltar
        InlineKeyboardButton backButton = new InlineKeyboardButton();
        backButton.setText("Voltar");
        backButton.setCallbackData("0");

        keyboard.add(List.of(backButton));
        
        return new InlineKeyboardMarkup(keyboard);
    }

    private void handleTextMessage(Update update) {
        long chatId = update.getMessage().getChatId();
        
        if (update.getMessage().getText().equals("/start")) {
            sendMainMenu(chatId);
        }
    }

    private void sendMainMenu(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(responseGenerator.generateResponse(0));
        message.setReplyMarkup(createMainMenuKeyboard());
        
        try {
            execute(message);
        } catch (TelegramApiException e) {
            sendErrorMessage(chatId);
        }
    }

    private InlineKeyboardMarkup createMainMenuKeyboard() {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        
        // Linha 1 - Line atual
        InlineKeyboardButton lineupButton = new InlineKeyboardButton();
        lineupButton.setText("Apresentação");
        lineupButton.setCallbackData(String.valueOf(1));
        
        // Linha 2 - Ajuda com a loja
        InlineKeyboardButton storeButton = new InlineKeyboardButton();
        storeButton.setText("Projetos");
        storeButton.setCallbackData(String.valueOf(2));
        
        // Linha 3 - Dúvidas sobre jogos
        InlineKeyboardButton gamesButton = new InlineKeyboardButton();
        gamesButton.setText("Contato");
        gamesButton.setCallbackData(String.valueOf(3));
        
        // Adiciona os botões em linhas separadas
        keyboard.add(List.of(lineupButton));
        keyboard.add(List.of(storeButton));
        keyboard.add(List.of(gamesButton));
        
        return new InlineKeyboardMarkup(keyboard);
    }

    private InlineKeyboardMarkup createBackButton() {
        InlineKeyboardButton backButton = new InlineKeyboardButton();
        backButton.setText("Voltar ao menu");
        backButton.setCallbackData("0");
        
        return new InlineKeyboardMarkup(List.of(List.of(backButton)));
    }

    private void sendErrorMessage(long chatId) 
    {
        SendMessage errorMsg = new SendMessage();
        errorMsg.setChatId(String.valueOf(chatId));
        errorMsg.setText("⚠️ Ocorreu um erro inesperado. Tente novamente mais tarde.");
        
        try 
        {
            execute(errorMsg);
        } 
        catch (TelegramApiException e) 
        {
            LOGGER.log(Level.SEVERE, "Falha ao enviar mensagem de erro", e);
        }
    }

}
