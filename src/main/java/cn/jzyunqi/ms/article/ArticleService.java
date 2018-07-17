package cn.jzyunqi.ms.article;

import cn.jzyunqi.common.utils.BeanUtilPlus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private IArticleDao articleDao;

    public List<ArticleDto> articles() {
        List<Article> articles = articleDao.findAll();
        return articles.stream().map(article -> {
            return BeanUtilPlus.copyAs(article, ArticleDto.class);
        }).collect(Collectors.toList());
    }
}
