import daos.DataSourceProvider;
import daos.NewsArticleDao;
import entities.NewsArticle;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;

public class NewsArticleDaoTestCase {

    private NewsArticleDao newsArticleDao = new NewsArticleDao();

    @Before
    public void initDatabase() throws Exception {
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM fr_article");
            this.insertDataSet(statement);
        }
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM en_article");
            this.insertDataSet(statement);
        }
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM news_article");
            this.insertDataSet(statement);
        }
    }

    public void insertDataSet(Statement statement) throws Exception {
        statement.executeUpdate("INSERT INTO news_article VALUES (1, '2018-02-19', '../../../img/logo-1.png', " +
                "'../../../img/logo-2.png', '../../../img/logo-3.png', 1, 1)");
        statement.executeUpdate("INSERT INTO fr_article VALUES (1, 'Titre article 1', 'Contenu article 1')");
        statement.executeUpdate("INSERT INTO en_article VALUES (1, 'Title article 1', 'Content article 1')");
        statement.executeUpdate("INSERT INTO news_article VALUES (2, '2018-02-19', '../../../img/bebe.png', " +
                "'../../../img/bonhomme.png', '../../../img/bonhomme2.png', 2, 2)");
        statement.executeUpdate("INSERT INTO fr_article VALUES (2, 'Titre article 2', 'Contenu article 2')");
        statement.executeUpdate("INSERT INTO en_article VALUES (2, 'Title article 2', 'Content article 2')");
    }

    @Test
    public void shouldGetNewsArticle() throws Exception {
        //WHEN
        NewsArticle newsArticle = newsArticleDao.getNewsArticle(1);
        //THEN
        Assertions.assertThat(newsArticle.getId()).isEqualTo(1);
        Assertions.assertThat(newsArticle.getDate()).isEqualTo(1);
        Assertions.assertThat(newsArticle.getPhoto1()).isEqualTo("../../../img/logo-1.png");
        Assertions.assertThat(newsArticle.getPhoto2()).isEqualTo("../../../img/logo-2.png");
        Assertions.assertThat(newsArticle.getPhoto3()).isEqualTo("../../../img/logo-3.png");
        Assertions.assertThat(newsArticle.getTitleFR()).isEqualTo("Titre article 1");
        Assertions.assertThat(newsArticle.getTitleEN()).isEqualTo("Title article 1");
        Assertions.assertThat(newsArticle.getContentFR()).isEqualTo("Contenu article 1");
        Assertions.assertThat(newsArticle.getContentEN()).isEqualTo("Content article 1");
    }
}
