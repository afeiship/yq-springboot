package cn.jzyunqi.ms.article;

import cn.jzyunqi.common.model.RestResultDto;
import cn.jzyunqi.common.utils.BeanUtilPlus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private IArticleDao articleDao;

    public List<ArticleDto> list() {
        List<Article> articles = articleDao.findAll();
        return articles.stream().map(article -> {
            return BeanUtilPlus.copyAs(article, ArticleDto.class);
        }).collect(Collectors.toList());
    }


    @Transactional
    public Article create(ArticleDto articleDto) {
        Article article = new Article();
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        return articleDao.save(article);
    }

    @Transactional
    public Article update(ArticleDto articleDto) {
        Article article = articleDao.findById(articleDto.getId()).orElse(new Article());
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        return articleDao.save(article);
    }


    @Transactional
    public void deleteById(Long id) {
        articleDao.deleteById(id);
    }


}
