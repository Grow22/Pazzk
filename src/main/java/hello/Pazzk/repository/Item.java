package hello.Pazzk.repository;

import lombok.Data;


// 게시판에서 저장할 객체 Item
@Data
public class Item {


    private Long id;

    private String itemName;
    private String thumbnailUrl;

    private String url;
    // 좋아요
    private Long like = 0L;

    // 싫어요
    private Long disLike = 0L;

    private String videoId;


    public Item(Long id) {
        this.id = id;
    }

    // 좋아요 증가 메서드
    public void likePlus() {
        this.like++;
    }

    public Item() {
    }

    public Item(String itemName, String thumbnailUrl, String videoId) {
        this.itemName = itemName;
        this.thumbnailUrl = thumbnailUrl;
        this.videoId = videoId;
    }

    public Item(Long id, String itemName, String thumbnailUrl) {
        this.id = id;
        this.itemName = itemName;
        this.thumbnailUrl = thumbnailUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }


    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Long getLike() {
        return like;
    }

    public void setLike(Long like) {
        this.like = like;
    }

    public Long getDisLike() {
        return disLike;
    }

    public void setDisLike(Long disLike) {
        this.disLike = disLike;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}