package com.werik.sip.basicproxy.MessageBus;

public abstract class Message
{
    MessageHeader header;

    public Message(MessageHeader header)
    {
        this.header = header;
    }

    public MessageHeader getHeader()
    {
        return header;
    }


    // add own data fields during class extension
}
