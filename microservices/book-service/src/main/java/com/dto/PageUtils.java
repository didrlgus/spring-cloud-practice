package com.dto;

import lombok.Getter;

@Getter
public class PageUtils {

    private int page = 1;                   // 현재 페이지
    private int scaleStartPage = 1;         // scale 시작 페이지
    private int scaleEndPage = 1;           // scale 끝 페이지
    private int totalPage = 1;              // 전체 페이지 수
    private int prevPage = 0;               // 이전 페이지 ('<<' 버튼 클릭 시)
    private int nextPage = 0;               // 다음 페이지 ('>>' 버튼 클릭 시)

    public void setPagingInfo(int pageNumber, int totalPage, int scaleSize) {
        this.page = pageNumber + 1;

        int nowScale = (page - 1) / scaleSize + 1;               	    // 현재 화면의 스케일 그룹

        int startPage = (nowScale - 1) * scaleSize + 1;            	    // 스케일 시작 페이지
        int endPage = startPage + scaleSize - 1;               		    // 스케일 끝 페이지

        endPage = Math.min(endPage, totalPage);

        this.scaleStartPage = startPage;
        this.scaleEndPage = endPage;
        this.prevPage = startPage - 1;
        this.nextPage = endPage + 1;
        this.totalPage = totalPage;
    }

}
