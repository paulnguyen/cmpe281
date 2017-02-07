package jpa.mapping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		
		ApplicationContext context = SpringApplication.run(Application.class, args);

		UserRepository userRepository = context.getBean(UserRepository.class);
		ArticleRepository articleRepository = context.getBean(ArticleRepository.class);
		UserVoteArticleRepository userVoteArticleRepository = context.getBean(UserVoteArticleRepository.class);
		
		// Add new user: alex
		User alex = new User();
		alex.setFullname("Alex Watson");
		alex.setUsername("w.alex");
		alex.setPassword("1234");
		alex = userRepository.save(alex);
		
		// Add new article
		Article article = new Article();
		article.setTitle("Title of article");
		article.setContent("Content of article");
		article = articleRepository.save(article);

		// alex votes up for this article
		UserVoteArticle userVoteArticle = new UserVoteArticle();
		userVoteArticle.setArticle(article);
		userVoteArticle.setUser(alex);
		userVoteArticle.setVoteType(VoteType.VOTE_UP);
		userVoteArticleRepository.save(userVoteArticle);
		
	}
}
