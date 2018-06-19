package com.werik.sip.basicproxy.SipParser;

public interface ISipMessageParser
{
    public SipMessage parse(String message);
}
