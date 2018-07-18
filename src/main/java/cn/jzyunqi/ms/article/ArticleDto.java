package cn.jzyunqi.ms.article;

import cn.jzyunqi.common.model.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleDto extends BaseDto<Long, Long> {
    private String title;
    private String content;
    private String description;
}
