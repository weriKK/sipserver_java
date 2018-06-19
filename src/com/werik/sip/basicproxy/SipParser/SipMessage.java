package com.werik.sip.basicproxy.SipParser;

import com.werik.sip.basicproxy.MessageBus.Message;
import com.werik.sip.basicproxy.MessageBus.MessageHeader;

import java.util.HashMap;
import java.util.Map;

public class SipMessage
{
    private SipMessageType type;

    // Request
    private String method;
    private String uri;

    // Response
    private int statusCode;
    private String reasonPhrase;


    private Map<String, String> headers;
    private int contentLength;
    private String payload;


    private String rawMessage;

    public SipMessage(String message)
    {
        rawMessage = message;
        type = SipMessageType.UNKNOWN;
        headers = new HashMap<>();
        contentLength = 0;
        payload = "";
    }

    public SipMessageType getType()
    {
        return type;
    }

    public void setType(SipMessageType type)
    {
        this.type = type;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    public int getStatusCode()
    {
        return statusCode;
    }

    public void setStatusCode(int statusCode)
    {
        this.statusCode = statusCode;
    }

    public String getReasonPhrase()
    {
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase)
    {
        this.reasonPhrase = reasonPhrase;
    }

    public Map<String, String> getHeaders()
    {
        return headers;
    }

    public String getHeaderValue(String headerName)
    {
        return headers.get(headerName);
    }

    public void addHeader(String headerName, String headerValue )
    {
        this.headers.put(headerName, headerValue);
    }

    public int getContentLength()
    {
        return contentLength;
    }

    public void setContentLength(int contentLength)
    {
        this.contentLength = contentLength;
    }

    public String getPayload()
    {
        return payload;
    }

    public void setPayload(String payload)
    {
        this.payload = payload;
    }

    public String getRawMessage()
    {
        return rawMessage;
    }
}
