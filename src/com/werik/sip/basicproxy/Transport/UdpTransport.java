package com.werik.sip.basicproxy.Transport;

import java.io.IOException;
import java.net.*;

public class UdpTransport extends Thread
{
    private DatagramSocket listenerSocket;
    private boolean running;

    private static final int MAX_DATAGRAM_BUFFER_SIZE = 65535;
    public static final int WELL_KNOWN_PORT = 5060;

    public UdpTransport()
    {
        running = false;

        try
        {
            listenerSocket = new DatagramSocket(WELL_KNOWN_PORT);
            System.out.println("[TRP] Listening on " + listenerSocket.getLocalAddress().toString() + ":" +listenerSocket.getLocalPort());
        }
        catch (SocketException e)
        {
            e.printStackTrace();
        }
    }

    public void stopTransport() throws InterruptedException
    {
        running = false;
        listenerSocket.close();
        System.out.println("[TRP] Received Stop Request");
    }

    public boolean isReady()
    {
        return running;
    }

    public void send(InetAddress address, int port, String message) throws IOException
    {
        // Send: Socket has no port, routing is done by packet
        DatagramSocket senderSocket = new DatagramSocket();

        byte[] buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);

        System.out.println("[TRP] Sending: " + message);
        senderSocket.send(packet);
        senderSocket.close();
    }

    public DatagramPacket receive() throws IOException
    {
        // receive: Socket port defines where we listen
        byte[] recvBuffer = new byte[MAX_DATAGRAM_BUFFER_SIZE];
        DatagramPacket packet = new DatagramPacket(recvBuffer, recvBuffer.length);

        // BLOCKING!
        listenerSocket.receive(packet);
        System.out.println("[TRP] Received: " + new String(packet.getData(),0 , packet.getLength()));

        return packet;
    }

    public void run()
    {
        try
        {
            running = true;
            while (running)
            {
                // Send Message
                // @TODO receive should not return packet?! This whole thing needs rewriting
                DatagramPacket packet = receive(); // Blocking!
                String msg = new String(packet.getData(), 0, packet.getLength());
                if (msg.equals("Marco!"))
                {
                    send(packet.getAddress(), packet.getPort(), "Polo!");
                }
                // @TODO Non-Blocking UDP
                // http://tutorials.jenkov.com/java-nio/datagram-channel.html
                // http://tutorials.jenkov.com/java-nio/datagram-channel.html
                // http://tutorials.jenkov.com/java-nio/datagram-channel.html

                //SipMessageParser msg = new SipMessageParser(packet);
                //msg.Debug();



                // receive packet
                // parse SipMessageParser
                // signal message receievd event

                // we should be able to design the system with only writing interfaces
                // they define what each object must do?
                // eg: ISipParser - must be able to parse the incoming message and return a SipMessageParser
                //     DatagramSipParser implements ISipParser -> parses udp datagram packet and returns SipMessageParser
                // Keep it very generic for the interface, define only the contracts first, implementations can come later

            }
        }
        catch (SocketException e)
        {
            if (!running && e.getMessage().equals("socket closed"))
            {
                // Sockets are forced to close when the thread is stopped
                // this is expected
            }
            else
            {
                e.printStackTrace();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            listenerSocket.close();
        }
    }
}
