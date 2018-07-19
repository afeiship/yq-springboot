package cn.jzyunqi.ms.article;

import cn.jzyunqi.common.model.PageDto;
import cn.jzyunqi.common.model.RestResultDto;
import cn.jzyunqi.common.support.SqlFilter;
import cn.jzyunqi.common.utils.BeanUtilPlus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private IArticleDao articleDao;

    private ArticleDto entityToDto(Article article) {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setTitle(article.getTitle());
        articleDto.setContent(article.getContent());
        return articleDto;
    }

    public PageDto<ArticleDto> query(Pageable pageable) {
        Page<Article> articles = articleDao.findAll(pageable);
        List<ArticleDto> articleDtos = articles.stream().map(this::entityToDto).collect(Collectors.toList());
        return new PageDto<ArticleDto>(articleDtos, articles.getTotalElements());
    }

    public PageDto<ArticleDto> queryByTitle(String title, Pageable pageable) {
        Page<Article> articles = articleDao.findByTitle(SqlFilter.filterForLike(title), pageable);
        List<ArticleDto> articleDtos = articles.stream().map(this::entityToDto).collect(Collectors.toList());
        return new PageDto<ArticleDto>(articleDtos, articles.getTotalElements());
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
