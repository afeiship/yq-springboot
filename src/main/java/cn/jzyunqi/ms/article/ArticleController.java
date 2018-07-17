package cn.jzyunqi.ms.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/articles")
    public List<ArticleDto> articles() {
        return articleService.articles();
    }
}
