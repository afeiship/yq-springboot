package cn.jzyunqi.ms.article;

import cn.jzyunqi.common.model.RestResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping(value = "/articles/list")
    public RestResultDto list() {
        return RestResultDto.success(articleService.list());
    }

    @PostMapping(value = "/articles/create")
    public RestResultDto create(ArticleDto articleDto) {
        return RestResultDto.success(articleService.create(articleDto));
    }

    @PostMapping(value = "/articles/update")
    public RestResultDto update(ArticleDto articleDto) {
        return RestResultDto.success(articleService.update(articleDto));
    }

    @PostMapping(value = "/articles/delete")
    public RestResultDto deleteById(Long id) {
        articleService.deleteById(id);
        return RestResultDto.success();
    }
}
