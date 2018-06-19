package com.werik.sip.basicproxy.Registrar;

/*
REGISTER sip:192.168.1.100:5060;transport=udp;lr SIP/2.0
Via: SIP/2.0/UDP 192.168.1.102:57307;rport;branch=z9hG4bKPjy8p8oErecpLr7MBfks8fgqOB57sNNKAM
Max-Forwards: 70
From: "TestDisplayName" <sip:testlogin@192.168.1.100>;tag=t1cCAf9FzzGAJlf.VvTuq8yXrC8zFw86
To: "TestDisplayName" <sip:testlogin@192.168.1.100>
Call-ID: 8e6vOJAFF7Fpy9WLW-L.eTW1bpbmIyYq
CSeq: 40002 REGISTER
User-Agent: CSipSimple_greatlte-26/r2457
Contact: "TestDisplayName" <sip:testlogin@192.168.1.102:57307;ob>
Expires: 900
Allow: PRACK, INVITE, ACK, BYE, CANCEL, UPDATE, INFO, SUBSCRIBE, NOTIFY, REFER, MESSAGE, OPTIONS
Content-Length:  0
 */

import com.werik.sip.basicproxy.SipParser.SipMessageParser;

import java.util.List;

public class Registrar
{
    List<String> bindingList;

    public void registerUser(SipMessageParser message)
    {

    }
}
