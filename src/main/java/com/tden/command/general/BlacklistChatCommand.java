package com.tden.command.general;

import com.tden.TDGameBot;
import com.tden.command.BasicCommandImpl;
import com.tden.command.CommandModel;
import com.tden.command.CommandPrivacy;
import com.tden.utilities.Responses;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by Stanislav Myachenkov on 10.12.2016.
 */
@Slf4j
public class BlacklistChatCommand extends BasicCommandImpl {

    public BlacklistChatCommand(Message message, TDGameBot bot, String argString) {
        super(bot, message, CommandModel.CommandType.BLACKLISTCHAT, argString);
    }

    @Override
    public void processCommand() {
        CommandPrivacy p = bot.getCommandPrivacy();

        try {
            p.removeChat(message.getChatId().toString());

            SendMessage reply = new SendMessage();
            reply.setChatId(message.getChatId());
            reply.setReplyToMessageId(message.getMessageId());
            reply.setText(Responses.CHAT_DISAPPROVED.toString());
            reply.enableMarkdown(true);
            bot.sendMessage(reply);

        } catch (TelegramApiException e) {
            log.error(String.format("Error processing command [ %s ] in message [ %s ] from user [ %s ]", getCommandName(), message, message.getFrom()));
        }

    }

}
