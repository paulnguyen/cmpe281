package jpa.mapping;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "articles")
public class Article implements Serializable {

	private static final long serialVersionUID = -7193355058800237746L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "content")
	private String content;

	@OneToMany(mappedBy = "pk.article")
	private List<UserVoteArticle> userVoteArticle;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<UserVoteArticle> getUserVoteArticle() {
		return userVoteArticle;
	}

	public void setUserVoteArticle(List<UserVoteArticle> userVoteArticle) {
		this.userVoteArticle = userVoteArticle;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
