package jpa.mapping;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class UserVoteArticleId implements Serializable {

	private static final long serialVersionUID = -6895144151037881773L;

	@ManyToOne
	private User user;
	
	@ManyToOne
	private Article article;
	
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserVoteArticleId that = (UserVoteArticleId) o;

        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (article != null ? !article.equals(that.article) : that.article != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (user != null ? user.hashCode() : 0);
        result = 31 * result + (article != null ? article.hashCode() : 0);
        return result;
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}
