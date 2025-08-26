package hello.Pazzk.domain;


import lombok.Data;

@Data
public class Information {

    public String keyword;

    public Information(String keyword) {
        this.keyword = keyword;
    }
}