package werik.sip.tests;

import com.werik.sip.basicproxy.Transport.UdpTransport;
import org.junit.*;

import java.io.IOException;
import java.net.*;

import static org.junit.Assert.*;

public class TransportTest
{
    static final int WELL_KNOWN_PORT = 5060;

    private static UdpTransport udpTransport;

    @BeforeClass
    public static void beforeTestSuite() throws InterruptedException {

        if ( null == udpTransport )
        {
            udpTransport = new UdpTransport();
        }

        udpTransport.start();

        while (!udpTransport.isReady())
        {
            Thread.sleep(5);
        }
        Thread.sleep(5);
    }

    @Before
    public void beforeEachTest() throws InterruptedException {
        if (udpTransport != null && !udpTransport.isAlive())
        {
            beforeTestSuite();
        }
    }

    @AfterClass
    public static void afterTestSuite() throws InterruptedException {
        udpTransport.stopTransport();
    }

    @Test
    public void listensOnWellKnownPortAndReplies() throws IOException
    {
        byte[] buffer = "Marco!".getBytes();
        InetAddress address = InetAddress.getLocalHost();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, WELL_KNOWN_PORT);
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(10); // in ms
        socket.send(packet);

        socket.receive(packet);
        String message = new String(packet.getData(),0,packet.getLength());
        assertEquals("Polo!", message);

        socket.close();
    }

    @Test
    public void repliesToMultipleMessages() throws IOException
    {
        String message;
        byte[] buffer = new byte[10];
        DatagramPacket packet;

        InetAddress address = InetAddress.getLocalHost();
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(10); // in ms

        for ( int i = 0; i < 10; i++)
        {
            buffer = "Marco!".getBytes();
            packet = new DatagramPacket(buffer, buffer.length, address, WELL_KNOWN_PORT);
            socket.send(packet);
            socket.receive(packet);
            message = new String(packet.getData(), 0, packet.getLength());
            assertEquals("Polo!", message);
        }

        socket.close();

    }

    @Test
    public void canSendMessage() throws IOException, InterruptedException {
        int port = 5080;
        InetAddress address = InetAddress.getLocalHost();

        byte[] buffer = new byte[12];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        DatagramSocket socket = new DatagramSocket(port);
        socket.setSoTimeout(500); // in ms

        udpTransport.send(address, port, "Marco Polo!");

        socket.receive(packet);
        String message = new String(packet.getData(), 0, packet.getLength());
        assertEquals("Marco Polo!", message);

        socket.close();
    }

    @Test
    public void canStopTransport()
    {
        try {
            udpTransport.stopTransport();

            final long maximumAllowedWaitTime = 10; // in ms
            long timeNow = System.currentTimeMillis();

            while ( (System.currentTimeMillis() - timeNow) < maximumAllowedWaitTime)
            {
                if ( !udpTransport.isAlive() )
                {
                    assertTrue(true);
                    return;
                }
            }

            assertFalse(udpTransport.isAlive());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
