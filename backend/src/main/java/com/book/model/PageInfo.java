package com.book.model;

public class PageInfo {

    private int pageNumber;
    private int limit;

    public PageInfo(int pageNumber, int limit){
        this.pageNumber = pageNumber;
        this.limit = limit;
    }

    public PageInfo(){
        this.pageNumber = 1;
        this.limit = 100;
    }

    public PageInfo pageNumber(int pageNumber){
        this.pageNumber = pageNumber;
        return this;
    }

    public PageInfo limit(int limit){
        this.limit = limit;

        return this;
    }
}
