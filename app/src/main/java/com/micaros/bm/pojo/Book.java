package com.micaros.bm.pojo;

public class Book {
    private Integer id;
    private String BookId;//书号（图书的ISBN）
    private String BookName;
    private String BookType;
    private String BookWriter;
    private String BookPublicer;//出版社
    private String BookPrice;
    private String BookRank;//等级或者评分
    private String BookComment;//简介
    private String img;

    public Book() {
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", BookId='" + BookId + '\'' +
                ", BookName='" + BookName + '\'' +
                ", BookType='" + BookType + '\'' +
                ", BookWriter='" + BookWriter + '\'' +
                ", BookPublicer='" + BookPublicer + '\'' +
                ", BookPrice='" + BookPrice + '\'' +
                ", BookRank='" + BookRank + '\'' +
                ", BookComment='" + BookComment + '\'' +
                ", img='" + img + '\'' +
                '}';
    }

    public Book(Integer id, String bookId, String bookName, String bookType, String bookWriter, String bookPublicer, String bookPrice, String bookRank, String bookComment, String img) {
        this.id = id;
        BookId = bookId;
        BookName = bookName;
        BookType = bookType;
        BookWriter = bookWriter;
        BookPublicer = bookPublicer;
        BookPrice = bookPrice;
        BookRank = bookRank;
        BookComment = bookComment;
        this.img = img;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getBookType() {
        return BookType;
    }

    public void setBookType(String bookType) {
        BookType = bookType;
    }

    public String getBookWriter() {
        return BookWriter;
    }

    public void setBookWriter(String bookWriter) {
        BookWriter = bookWriter;
    }

    public String getBookPublicer() {
        return BookPublicer;
    }

    public void setBookPublicer(String bookPublicer) {
        BookPublicer = bookPublicer;
    }

    public String getBookPrice() {
        return BookPrice;
    }

    public void setBookPrice(String bookPrice) {
        BookPrice = bookPrice;
    }

    public String getBookRank() {
        return BookRank;
    }

    public void setBookRank(String bookRank) {
        BookRank = bookRank;
    }

    public String getBookComment() {
        return BookComment;
    }

    public void setBookComment(String bookComment) {
        BookComment = bookComment;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
