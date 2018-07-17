package cn.jzyunqi.ms.article;

import cn.jzyunqi.common.persistence.domain.BaseDomain;

import javax.persistence.Entity;

@Entity
public class Article extends BaseDomain<Long, Long> {
    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
