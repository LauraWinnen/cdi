package org.axonframework.cdi;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;

import java.util.Optional;
import javax.enterprise.inject.spi.Bean;

/**
 * @author Milan Savic
 */
class MessageHandlingBean {

    private final Bean<?> bean;
    private final boolean eventHandler;
    private final boolean queryHandler;
    private final boolean commandHandler;

    MessageHandlingBean(Bean<?> bean, boolean eventHandler, boolean queryHandler, boolean commandHandler) {
        this.bean = bean;
        this.eventHandler = eventHandler;
        this.queryHandler = queryHandler;
        this.commandHandler = commandHandler;
    }

    static Optional<MessageHandlingBean> inspect(Bean<?> bean) {
        boolean isEventHandler = CdiUtilities.hasAnnotatedMethod(bean, EventHandler.class);
        boolean isQueryHandler = CdiUtilities.hasAnnotatedMethod(bean, QueryHandler.class);
        boolean isCommandHandler = CdiUtilities.hasAnnotatedMethod(bean, CommandHandler.class);

        if (isEventHandler || isQueryHandler || isCommandHandler) {
            return Optional.of(new MessageHandlingBean(bean, isEventHandler, isQueryHandler, isCommandHandler));
        }

        return Optional.empty();
    }

    public Bean<?> getBean() {
        return bean;
    }

    public boolean isEventHandler() {
        return eventHandler;
    }

    public boolean isQueryHandler() {
        return queryHandler;
    }

    public boolean isCommandHandler() {
        return commandHandler;
    }
}
