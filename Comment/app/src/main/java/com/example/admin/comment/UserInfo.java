package com.example.admin.comment;

/**
 * Created by admin on 06.10.2017.
 */

public class UserInfo {
    public String username;
    public String comment;

    public UserInfo(String newUsername, String newComment){
        username = newUsername;
        comment = newComment;
    }
    public String getComment() {
        return comment;
    }
    public String getUsername() {
        return username;
    }
}
