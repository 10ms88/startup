package com.company.startup.core.service.telegram;

import com.company.startup.core.service.AppService;
import com.company.startup.core.service.WbService;
import com.company.startup.model.constants.TelegramConfig;
import com.haulmont.cuba.core.sys.AppContext;
import com.haulmont.cuba.core.sys.SecurityContext;
import com.haulmont.cuba.security.app.UserSessionsAPI;
import com.haulmont.cuba.security.auth.AuthenticationDetails;
import com.haulmont.cuba.security.auth.AuthenticationManager;
import com.haulmont.cuba.security.auth.LoginPasswordCredentials;
import com.haulmont.cuba.security.global.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;


@Component(TelegramBotBean.NAME)
public class TelegramBotBean extends TelegramLongPollingBot {

    @Inject
    private AppService appService;
    @Inject
    private WbService wbService;
    @Inject
    private AuthenticationManager authenticationManager;
    @Inject
    private UserSessionsAPI userSessionsAPI;
    private static UserSession userSession;
    public static final String NAME = "TelegramBotBean";

    private static final Logger log = LoggerFactory.getLogger(TelegramBotBean.class);
    private static final String TELEGRAM_EXCEPTION_TEXT = "LogsFetchingBot.init error: {}";

    private static final String SUCCESS_MESSAGE = "Запуск бота завершился успешно";

    private static String token;
    private static String username;
    private static String login;
    private static String password;
    private static int attemptLimit;
    private static String START = "/start";
    private static String BUY = "/buyTL";
    private static String STAT = "/getStat";
    private static String ORDERS = "/getOrders";
    private static String STOCKS = "/getStocks";
    private static String STAT_TODAY = "/getStatToday";


    @PostConstruct
    public void init() {
        username = TelegramConfig.USERNAME;
        token = TelegramConfig.TOKEN;
        login = TelegramConfig.LOGIN;
        password = TelegramConfig.PASSWORD;
        attemptLimit = TelegramConfig.ATTEMPT_LIMIT;
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (Exception e) {
            log.error(TELEGRAM_EXCEPTION_TEXT, e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        setSession();
        if (update.hasMessage()) {
            handleMessage(update.getMessage());
        }
    }

    private void handleMessage(Message message) {
        if (message.hasText() && message.hasEntities()) {
            Optional<MessageEntity> commandEntity =
                    message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();
            if (commandEntity.isPresent()) {
                String command = message.getText()
                        .substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
                if (START.equals(command)) {
                    sendMessage(getText(message, true), message.getChatId());
                    return;
                }
                if (STAT.equals(command)) {
                    sendMessage(wbService.getStatistic(false), message.getChatId());
                    return;
                }
                if (STAT_TODAY.equals(command)) {
                    sendMessage(wbService.getStatistic(true), message.getChatId());
                    return;
                }
                if (ORDERS.equals(command)) {
                    sendMessage(wbService.getOrders(), message.getChatId());
                    return;
                }
                if (STOCKS.equals(command)) {
                    sendMessage(wbService.getStocks(), message.getChatId());
                    return;
                }
            }
        }

        if (message.hasText()) {

            sendMessage("Некорректно введены данные, доступные команды\n"
                            + STAT_TODAY + "\n" + STAT + "\n" + ORDERS + "\n" + STOCKS
                    , message.getChatId());

        }
    }


    private String getText(Message message, boolean isCommand) {
        if (isCommand) return SUCCESS_MESSAGE;

        return appService.getPriseInfo(message.getText().toUpperCase());

    }

    public void sendMessage(@NotNull String message, @NotNull Long chatId) {
        try {
            execute(
                    SendMessage.builder()
                            .text(message)
                            .chatId(chatId)
                            .build());
        } catch (TelegramApiException e) {
            log.error(TELEGRAM_EXCEPTION_TEXT, e.getMessage());
        }
    }


    private void setSession() {
        if (userSession == null || !checkUserSessions(AppContext.getSecurityContext())) {
            LoginPasswordCredentials loginPasswordCredentials =
                    new LoginPasswordCredentials(login, password);
            AuthenticationDetails authenticationDetails =
                    authenticationManager.login(loginPasswordCredentials);
            userSession = authenticationDetails.getSession();
            AppContext.setSecurityContext(new SecurityContext(userSession));
        }
    }


    private boolean checkUserSessions(SecurityContext securityContext) {
        return securityContext != null
                && userSessionsAPI.get(securityContext.getSessionId()) != null;
    }

    private String buyTL() {


        BigDecimal USDTTRY = new BigDecimal(appService.getPriseInfo("USDTTRY"));
        BigDecimal USDTRUB = new BigDecimal(appService.getPriseInfo("USDTRUB"));

        BigDecimal buyWithCommissionUSDTTRY = USDTTRY.multiply(BigDecimal.valueOf(0.99));
        BigDecimal buyWithCommissionUSDTRUB = USDTRUB.multiply(BigDecimal.valueOf(1.01));

        BigDecimal sellWithCommissionUSDTTRY = USDTTRY.multiply(BigDecimal.valueOf(1.01));
        BigDecimal sellWithCommissionUSDTRUB = USDTRUB.multiply(BigDecimal.valueOf(0.99));


        BigDecimal buyPrice = USDTRUB.divide(USDTTRY, 2, RoundingMode.CEILING).setScale(2, RoundingMode.HALF_UP);
        BigDecimal withCommissionBuyPrice = buyWithCommissionUSDTRUB.divide(buyWithCommissionUSDTTRY, 2, RoundingMode.CEILING).setScale(2, RoundingMode.HALF_UP);
        BigDecimal withCommissionSellPrice = sellWithCommissionUSDTRUB.divide(sellWithCommissionUSDTTRY, 2, RoundingMode.CEILING).setScale(2, RoundingMode.HALF_UP);

        return "USDT/TRY: " + USDTTRY +
                "\nUSDT/RUB: " + USDTRUB +
                "\nPrice: " + buyPrice.toString() +
                "\nbuyPrice: " + withCommissionBuyPrice.toString() +
                "\nsellPrice: " + withCommissionSellPrice.toString();
    }

}