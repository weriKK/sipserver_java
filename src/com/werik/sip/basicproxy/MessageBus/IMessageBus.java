package com.werik.sip.basicproxy.MessageBus;

public interface IMessageBus
{
    void send(Message msg);
    Message poll(String topic);

    void registerMessageHandler(String topic, Observer observer);
    void removeMessageHandler(String topic, Observer observer);
}
