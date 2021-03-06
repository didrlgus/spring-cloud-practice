package com.utils.page;

import lombok.Getter;

@Getter
public class PageResponseData {

    private int page = 1;                   // 현재 페이지
    private int scaleStartPage = 1;         // scale 시작 페이지
    private int scaleEndPage = 1;           // scale 끝 페이지
    private int totalPage = 1;              // 전체 페이지 수
    private Integer prevPage;               // 이전 페이지 ('<<' 버튼 클릭 시)
    private Integer nextPage;               // 다음 페이지 ('>>' 버튼 클릭 시)

    public void setPagingInfo(int pageNumber, int totalPage, int scaleSize) {
        this.page = pageNumber + 1;

        int nowScale = (page - 1) / scaleSize + 1;               	    // 현재 화면의 스케일 그룹

        int startPage = (nowScale - 1) * scaleSize + 1;            	    // 스케일 시작 페이지
        int endPage = startPage + scaleSize - 1;               		    // 스케일 끝 페이지

        endPage = Math.min(endPage, totalPage);

        this.scaleStartPage = startPage;
        this.scaleEndPage = endPage;
        this.prevPage = (startPage - 1) > 0 ? (startPage - 1) : null;
        this.nextPage = (endPage + 1) <= totalPage ? (endPage + 1) : null;
        this.totalPage = totalPage;
    }

}
