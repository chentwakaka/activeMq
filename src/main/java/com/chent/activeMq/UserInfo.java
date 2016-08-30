package com.chent.activeMq;

import java.io.Serializable;

/**
 * Created by wangqingbin on 2016/8/30.
 */
public class UserInfo implements Serializable {
    private long jid;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getJid() {

        return jid;
    }

    public void setJid(long jid) {
        this.jid = jid;
    }



}
