package com.basho.msgy.Models;

import java.util.ArrayList;

public class Timeline {

    public enum TimelineType
    {
        Inbox,
        Sent;

        @Override
        public String toString() {
            if(this == Inbox)
                return "Inbox";
            else
                return "Sent";
        }
    }

    public Timeline() {
        Msgs = new ArrayList<String>();
    }

    public String Owner;
    public String Type;
    public ArrayList<String> Msgs;
}
