package cn.jzyunqi.ms.article;

import cn.jzyunqi.common.persistence.dao.BaseDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface IArticleDao extends BaseDao<Article, Long> {

//    @Query("select article from Article article where article.title like concat('%', ?1, '%') ")
    @Query("select article from Article article where article.title like ?1 escape '/'")
    Page<Article> findByTitle(String title, Pageable pageable);
}
