package cn.jzyunqi.ms.article;

import cn.jzyunqi.common.model.BaseDto;
import cn.jzyunqi.common.persistence.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
public class ArticleDto extends BaseDto<Long, Long> {
    private String title;
    private String content;
}
