package com.copasso.cocobill.model.bean.remote;

import cn.bmob.v3.BmobUser;

public class MyUser extends BmobUser {

    private String avatar;

    private int type;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
