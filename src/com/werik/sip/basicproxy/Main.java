package com.werik.sip.basicproxy;

import com.werik.sip.basicproxy.MessageBus.*;
import com.werik.sip.basicproxy.Registrar.Registrar;
import com.werik.sip.basicproxy.SipParser.SipMessage;
import com.werik.sip.basicproxy.Transport.UdpTransport;

public class Main {

    private Registrar registrar;

    public static class TestMsg extends Message
    {
        public SipMessage  sipMsg;

        public TestMsg(MessageHeader header, SipMessage msg)
        {
            super(header);

            sipMsg = msg;
        }
    }

    public static void main(String[] args) throws InterruptedException
    {
        // Singleton maybe? Pass it to every other modul
        // TODO: pass it to every other module
        // TODO: turn it into a thread, and use while isAlive on messageBus? or while until all layers are stopped?
        // TODO: unit tests for messagebus
        MessageBus messageBus = new MessageBus();

        messageBus.registerMessageHandler("Mokus", new Observer()
        {
            @Override
            public void onMessage(Message msg)
            {
                System.out.println("Observer received msg: " + ((TestMsg)msg).sipMsg.getRawMessage());
            }
        });

        Message msg = new TestMsg(new MessageHeader("Mokus", MessageType.SIP), new SipMessage("SIPMSG HERE!"));
        messageBus.send(msg);
        messageBus.send(msg);
        messageBus.send(msg);
        messageBus.send(msg);

// TODO THIS IS THE IDEA
//        class MessageBus
//        {
//            sendMessage(EventFilter, Msg) -> triggers notify for EventFilter
//        poll(EventFilter)
//
//
//            registerObserver(EventFilter)
//            removeObserver(EventFilter)
//            notify(EventFilter)
//        }
//
//        class Observer
//        {
//            // unblocks on notify
//            notify() { BlockingQueue.put(Msg); }
//
//            run()
//            {
//                while(1)
//                {
//                    Msg = BlockingQueue.take()
//                    process Msg;
//                }
//            }
//        }


//        Registrar reg = new Registrar(messageBus);
//        UdpTransport tp = new UdpTransport(messageBus); // runs in its own thread, constantly sending/receiving on udp
//        // Transaction ta = new Transaction(messageBus);
//
//
//        tp.start();
//
//        while (tp.isAlive())
//        {
//            tp.join();
//
//        }
    }
}
