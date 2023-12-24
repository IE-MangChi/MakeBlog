package com.blog.api.domain;

public class Page {
    private int currentPage; // 현재 페이지 번호
    private int boardSize; // 글 개수
    private int totalPage; // 페이지 번호 개수
    public Page(int pageNum, int count, int boardSize) {
            this.currentPage = pageNum;
            this.boardSize = 5;
            this.totalPage = (int) Math.ceil((double) count / this.boardSize);
    }
    public int getOffset() {
        return (currentPage - 1) * boardSize;
    }
}
