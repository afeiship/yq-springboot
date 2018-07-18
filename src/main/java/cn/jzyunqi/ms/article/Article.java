package cn.jzyunqi.ms.article;

import cn.jzyunqi.common.persistence.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Article extends BaseDomain<Long, Long> {
    private String title;
    private String content;
}
