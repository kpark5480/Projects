package com.muc;

public interface UserStatusListener {
    public void onLine(String login);
    public void offline(String login);
}
