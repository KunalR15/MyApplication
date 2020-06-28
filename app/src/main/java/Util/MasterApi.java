package Util;

import android.app.Application;

import java.security.PrivateKey;

public class MasterApi extends Application {

    private String Name;
    private String UserID;
    private static MasterApi instance;

    public static MasterApi getInstance(){
       if (instance == null)
           instance = new MasterApi();
       return instance;
    }

    public MasterApi(){}
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}
