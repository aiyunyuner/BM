package com.micaros.bm.pojo;


public class Borrow {
    private Integer id;
    public String BorName;
    public String BookId;
    public String BookName;
    public String BookAuthor;
    public String NowTime;

    @Override
    public String toString() {
        return "Borrow{" +
                "id=" + id +
                ", BorName='" + BorName + '\'' +
                ", BookId='" + BookId + '\'' +
                ", BookName='" + BookName + '\'' +
                ", BookAuthor='" + BookAuthor + '\'' +
                ", NowTime='" + NowTime + '\'' +
                '}';
    }

    public Borrow(Integer id, String borName, String bookId, String bookName, String bookAuthor, String nowTime) {
        this.id = id;
        BorName = borName;
        BookId = bookId;
        BookName = bookName;
        BookAuthor = bookAuthor;
        NowTime = nowTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBorName() {
        return BorName;
    }

    public void setBorName(String borName) {
        BorName = borName;
    }

    public String getBookId() {
        return BookId;
    }

    public void setBookId(String bookId) {
        BookId = bookId;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getBookAuthor() {
        return BookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        BookAuthor = bookAuthor;
    }

    public String getNowTime() {
        return NowTime;
    }

    public void setNowTime(String nowTime) {
        NowTime = nowTime;
    }
}
