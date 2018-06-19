package werik.sip.tests;

import com.werik.sip.basicproxy.SipParser.ISipMessageParser;
import com.werik.sip.basicproxy.SipParser.SipMessage;
import com.werik.sip.basicproxy.SipParser.SipMessageParser;
import com.werik.sip.basicproxy.SipParser.SipMessageType;

import org.junit.*;
import static org.junit.Assert.*;

public class SipMessageParserTest
{
    private static SipMessageParser sipParser;

    private static String TEST_REGISTER_REQUEST_STRING =
            "REGISTER sip:192.168.1.100:5060;transport=udp;lr SIP/2.0\r\n" +
            "Via: SIP/2.0/UDP 192.168.1.102:57307;rport;branch=z9hG4bKPjy8p8oErecpLr7MBfks8fgqOB57sNNKAM\r\n" +
            "Max-Forwards: 70\r\n" +
            "From: \"TestDisplayName\" <sip:testlogin@192.168.1.100>;tag=t1cCAf9FzzGAJlf.VvTuq8yXrC8zFw86\r\n" +
            "To: \"TestDisplayName\" <sip:testlogin@192.168.1.100>\r\n" +
            "Call-ID: 8e6vOJAFF7Fpy9WLW-L.eTW1bpbmIyYq\r\n" +
            "CSeq: 40002 REGISTER\r\n" +
            "User-Agent: CSipSimple_greatlte-26/r2457\r\n" +
            "Contact: \"TestDisplayName\" <sip:testlogin@192.168.1.102:57307;ob>\r\n" +
            "Expires: 900\r\n" +
            "Allow: PRACK, INVITE, ACK, BYE, CANCEL, UPDATE, INFO, SUBSCRIBE, NOTIFY, REFER, MESSAGE, OPTIONS\r\n" +
            "Content-Length: 43\r\n" +
            "\r\n" +
            "Hello\r\n" +
            "World!\r\n" +
            "This is Peter\r\n" +
            "Speaking!\r\n" +
            "\r\n";


    @BeforeClass
    public static void beforeTestSuite()
    {
    }

    @Before
    public void beforeEachTest()
    {
    }

    @Test
    public void canParseRegisterRequest()
    {
        ISipMessageParser sipParser = new SipMessageParser();
        SipMessage sipMessage = sipParser.parse(TEST_REGISTER_REQUEST_STRING);

        assertEquals(SipMessageType.REQUEST, sipMessage.getType());
        assertEquals("REGISTER", sipMessage.getMethod());
        assertEquals("sip:192.168.1.100:5060;transport=udp;lr", sipMessage.getUri());
        assertEquals("900", sipMessage.getHeaderValue("Expires"));

        assertEquals(43, sipMessage.getContentLength());
        assertEquals("Hello\r\nWorld!\r\nThis is Peter\r\nSpeaking!\r\n\r\n", sipMessage.getPayload());
//        assertEquals(sipMessage.getContentLength(), sipMessage.getPayload().length());
    }
}
