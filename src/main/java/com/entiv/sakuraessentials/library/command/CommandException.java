package com.entiv.sakuraessentials.library.command;

import com.entiv.sakuraessentials.library.message.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandException extends RuntimeException {

    private final String message;
    private final MessageType type;
    private final String[] variables;

    public CommandException(String message, MessageType type, String... variables) {
        this.message = message;
        this.type = type;
        this.variables = variables;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void sendMessage(CommandSender sender) {

        switch (type) {

            case INFO:

                Message.sendTip((Player) sender, message, variables);
                break;

            case WARN:

                Message.sendWarn((Player) sender, message, variables);
                break;

            case ERROR:

                Message.sendError((Player) sender, message, variables);
                break;

            case DEFAULT:

                Message.send(sender, message);
                break;
        }
    }

    public enum MessageType {
        INFO, WARN, ERROR, DEFAULT
    }
}
