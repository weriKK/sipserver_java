package com.werik.sip.basicproxy.MessageBus;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MessageBus implements IMessageBus
{
    ConcurrentMap<String,Set<Observer>> observerMap = new ConcurrentHashMap<>();

    @Override
    public void send(Message msg)
    {
        for ( Observer observer : observerMap.get(msg.header.messageBusTopic) )
        {
            observer.onMessage(msg);
        }

    }

    @Override
    public Message poll(String topic)
    {
        return null;
    }

    @Override
    public void registerMessageHandler(String topic, Observer observer)
    {
        if ( observerMap.containsKey(topic) )
        {
            observerMap.get(topic).add(observer);
        }
        else
        {
            Set<Observer> newList = new HashSet<>();
            newList.add(observer);
            observerMap.put(topic, newList);
        }
    }

    @Override
    public void removeMessageHandler(String topic, Observer observer)
    {
        if ( observerMap.containsKey(topic) )
        {
            observerMap.get(topic).remove(observer);
        }
    }
}
