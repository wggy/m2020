package org.wangep.customtag;

/***
 * created by wange on 2020/9/10 18:31
 */
public class User {
    private String id;
    private String userName;
    private String email;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }
}
