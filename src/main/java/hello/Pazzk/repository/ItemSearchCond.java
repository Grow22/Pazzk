package hello.Pazzk.repository;

import lombok.Data;

// 검색 조건으로 사용되는 객체 ItemSearchCond
@Data
public class ItemSearchCond {

    private Long itemId;

    private String itemName;

    public ItemSearchCond() {
    }


    public ItemSearchCond(Long itemId) {
        this.itemId = itemId;
    }

    public ItemSearchCond(String itemName) {
        this.itemName = itemName;
    }


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}