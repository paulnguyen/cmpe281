package jpa.mapping;

import java.io.Serializable;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "users_vote_articles")
@AssociationOverrides({ 
	@AssociationOverride(
			name = "pk.user", 
			joinColumns = @JoinColumn(name = "user_id")),
	@AssociationOverride(
			name = "pk.article", 
			joinColumns = @JoinColumn(name = "article_id")) 
})
public class UserVoteArticle implements Serializable {

	private static final long serialVersionUID = -1035308599077666061L;

	@EmbeddedId
	private UserVoteArticleId pk = new UserVoteArticleId();

	//@Enumerated(EnumType.ORDINAL)
	@Enumerated(EnumType.STRING)
	private VoteType voteType;

	@Transient
	public User getUser() {
		return this.pk.getUser();
	}

	public void setUser(User user) {
		this.pk.setUser(user);
	}

	@Transient
	public Article getArticle() {
		return this.pk.getArticle();
	}

	public void setArticle(Article article) {
		this.pk.setArticle(article);
	}

	public UserVoteArticleId getPk() {
		return pk;
	}

	public void setPk(UserVoteArticleId pk) {
		this.pk = pk;
	}

	public VoteType getVoteType() {
		return voteType;
	}

	public void setVoteType(VoteType voteType) {
		this.voteType = voteType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UserVoteArticle that = (UserVoteArticle) o;

		if (getPk() != null ? !getPk().equals(that.getPk()) : that.getPk() != null)
			return false;

		return true;
	}

	public int hashCode() {
		return (getPk() != null ? getPk().hashCode() : 0);
	}
}
