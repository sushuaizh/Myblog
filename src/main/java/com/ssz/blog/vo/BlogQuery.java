package com.ssz.blog.vo;

import com.ssz.blog.pojo.User;

/**
 * @author sushuaizhen
 * @date 2020/8/2
 */
public class BlogQuery {

    private String title;
    private Long typeId;
    private boolean recommend;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user;

    public BlogQuery() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }
}
