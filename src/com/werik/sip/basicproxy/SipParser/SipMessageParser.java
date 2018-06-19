package com.werik.sip.basicproxy.SipParser;

public class SipMessageParser implements ISipMessageParser
{
    private static final String SIP_VERSION_STRING = "SIP/2.0";


    public SipMessageParser()
    {
    }


    public SipMessage parse(String message) throws IllegalArgumentException
    {
        SipMessage  sipMessage = new SipMessage(message);
        StringBuilder payload = new StringBuilder();

        String[] lines = message.split("\r\n");

        setSipMessageTypeBasedOnStartLine(sipMessage, lines[0]);

        int currentLineIdx = 1;
        for (; currentLineIdx < lines.length; currentLineIdx++)
        {
            String line = lines[currentLineIdx].trim();

            // End of headers? Skip line and break
            if ( line.isEmpty() )
            {
                currentLineIdx++;
                break;
            }


            String hdr = line.substring(0, line.indexOf(':'));
            if ( !hdr.isEmpty() )
            {
                sipMessage.addHeader(hdr, line.substring(line.indexOf(':')+1).trim());
                if ( hdr.equals("Content-Length") )
                {
                    sipMessage.setContentLength(Integer.parseInt(sipMessage.getHeaderValue(hdr), 10));
                }
            }
            else
            {
                System.out.println("Empty Header Detected at line idx: " + currentLineIdx + ". Line: " + line );
            }

        }

        // Get the payload
        for (; currentLineIdx < lines.length; currentLineIdx++)
        {
            if ( 0 < sipMessage.getContentLength() )
            {
                String line = lines[currentLineIdx];
                payload.append(line);
                payload.append("\r\n"); // This was cut when the raw message string was split by \r\n
            }
        }

        // The final line of a valid payload is "\r\n", this was cut during the split()
        if ( 0 < sipMessage.getContentLength() )
        {
            payload.append("\r\n");
        }

        sipMessage.setContentLength(Integer.parseInt(sipMessage.getHeaderValue("Content-Length"), 10));
        sipMessage.setPayload(payload.toString());

        return sipMessage;
    }


    private void setSipMessageTypeBasedOnStartLine(SipMessage message, String line) throws IllegalArgumentException {
        String[] startLineParts = line.split(" ");

        if ( startLineParts.length != 3 ) {
            throw new IllegalArgumentException("Failed to parse SIP Message StartLine; Not enough parts.");
        }

        if ( isResponse(startLineParts[0]) )
        {
            message.setType(SipMessageType.RESPONSE);
            message.setStatusCode(Integer.parseInt(startLineParts[1], 10));
            message.setReasonPhrase(startLineParts[2]);
        }
        else if ( isRequest(startLineParts[0]) )
        {
            message.setType(SipMessageType.REQUEST);
            message.setMethod(startLineParts[0]);
            message.setUri(startLineParts[1]);
        }
        else
        {
            throw new IllegalArgumentException("Unknown SIP Message; Request method or status code not found");
        }
    }

    private boolean isResponse(String beginningOfStartLine)
    {
        // Responses start with "SIP/2.0"
        return beginningOfStartLine.equals(SIP_VERSION_STRING);
    }


    private boolean isRequest(String method)
    {
        // Requests start with a request method
        String[] supportedMethodList = new String[] { "REGISTER", "INVITE", "ACK", "CANCEL", "BYE", "OPTIONS" };

        for (String supportedMethod : supportedMethodList)
        {
            if (method.equals(supportedMethod))
            {
                return true;
            }
        }

        return false;
    }
}
