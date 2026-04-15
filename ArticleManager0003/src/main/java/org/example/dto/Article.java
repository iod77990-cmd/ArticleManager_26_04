package org.example.dto;

public class Article extends Dto {

    private String title;
    private String body;
    private int memberId;

    private String member;

    public Article(int id, String regDate, String updateDate, String title, String body, int memberId) {
        this.id = id;
        this.regDate = regDate;
        this.updateDate = updateDate;
        this.title = title;
        this.body = body;
        this.memberId = memberId;
    }

    public int getId() {
        return id;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getMemberId(){
        return memberId;
    }

    public void setMemberId(){
        this.memberId = memberId;
    }
}
