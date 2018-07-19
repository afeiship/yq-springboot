package cn.jzyunqi.ms.article;

import cn.jzyunqi.common.model.RestResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/articles/create")
    public RestResultDto create(ArticleDto articleDto) {
        return RestResultDto.success(articleService.create(articleDto));
    }

    @PostMapping("/articles/update")
    public RestResultDto update(ArticleDto articleDto) {
        return RestResultDto.success(articleService.update(articleDto));
    }

    @PostMapping("/articles/retrieve")
    public RestResultDto retrieve(Pageable pageable) {
        return RestResultDto.success(articleService.retrieve(pageable));
    }

    @PostMapping("/articles/delete")
    public RestResultDto deleteById(Long id) {
        articleService.deleteById(id);
        return RestResultDto.success();
    }
}
