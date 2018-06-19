package com.werik.sip.basicproxy.MessageBus;

public class MessageHeader
{
    String messageBusTopic;
    MessageType type;

    public MessageHeader(String messageBusTopic, MessageType type)
    {
        this.messageBusTopic = messageBusTopic;
        this.type = type;
    }

    public String getMessageBusTopic()
    {
        return messageBusTopic;
    }

    public MessageType getType()
    {
        return type;
    }
}
