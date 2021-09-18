/*
 * xkw.com Inc. Copyright (c) 2021 All Rights Reserved.
 */
package com.xkw.xop.client.response;

import java.util.List;

/**
 * PageData
 * 分页数据
 *
 * @author KQS
 * @since 2021/7/6
 */
public class PagerDto<T> {
    /**
     * 数据列表
     */
    private List<T> list;
    /**
     * 分页信息
     */
    private Pager pager;

    public PagerDto(List<T> list, Pager pager) {
        this.list = list;
        this.pager = pager;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public static class Pager {
        private Integer currentPageNo;
        private Integer pageSize;
        private Integer totalPageNo;
        private Integer totalCount;

        public Pager(Integer currentPageNo, Integer pageSize, Integer totalPageNo, Integer totalCount) {
            this.currentPageNo = currentPageNo;
            this.pageSize = pageSize;
            this.totalPageNo = totalPageNo;
            this.totalCount = totalCount;
        }

        public Integer getCurrentPageNo() {
            return currentPageNo;
        }

        public void setCurrentPageNo(Integer currentPageNo) {
            this.currentPageNo = currentPageNo;
        }

        public Integer getPageSize() {
            return pageSize;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }

        public Integer getTotalPageNo() {
            return totalPageNo;
        }

        public void setTotalPageNo(Integer totalPageNo) {
            this.totalPageNo = totalPageNo;
        }

        public Integer getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(Integer totalCount) {
            this.totalCount = totalCount;
        }
    }

}
