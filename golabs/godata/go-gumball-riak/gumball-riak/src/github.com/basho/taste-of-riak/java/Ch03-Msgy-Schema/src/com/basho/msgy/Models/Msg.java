package com.basho.msgy.Models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Msg {
    public String Sender;
    public String Recipient;
    public String Created;
    public String Text;

    public static Msg createNew(String sender, String recipient, String text) {
        Msg msg = new Msg();
        msg.Sender = sender;
        msg.Recipient = recipient;
        msg.Text = text;
        msg.Created = GetCurrentISO8601Timestamp();
        return msg;
    }

    private static String GetCurrentISO8601Timestamp() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        // Java Dates don't have microsecond resolution :(
        // Pad out to microseconds to match other examples.
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'000'");
        df.setTimeZone(tz);
        return df.format(new Date());
    }
}
