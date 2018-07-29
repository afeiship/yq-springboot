package cn.jzyunqi.ms.article;

import cn.jzyunqi.common.persistence.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "articles")
public class Article extends BaseDomain<Long, Long> {
    private String title;
    private String content;
}
