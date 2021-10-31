package com.poc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.poc.domainobject.Tutorial;
import com.poc.repository.TutorialRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
class SpringBootUnitTestDatajpatestExampleApplicationTests {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	TutorialRepository repository;

	@Test
	public void shouldFindNoTutorialsIfRepositoryIsEmpty() {
		Iterable<Tutorial> tutorials = repository.findAll();

		assertThat(tutorials).isEmpty();
	}

	@Test
	public void shouldStoreATutorial() {
		Tutorial tutorial = repository.save(new Tutorial("Tut title", "Tut desc", true));

		assertThat(tutorial).hasFieldOrPropertyWithValue("title", "Tut title");
		assertThat(tutorial).hasFieldOrPropertyWithValue("description", "Tut desc");
		assertThat(tutorial).hasFieldOrPropertyWithValue("published", true);
	}

	@Test
	public void shouldFindAllTutorials() {
		Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
		entityManager.persist(tut1);

		Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
		entityManager.persist(tut2);

		Tutorial tut3 = new Tutorial("Tut#3", "Desc#3", true);
		entityManager.persist(tut3);

		Iterable<Tutorial> tutorials = repository.findAll();

		assertThat(tutorials).hasSize(3).contains(tut1, tut2, tut3);
	}

	@Test
	public void shouldFindTutorialById() {
		Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
		entityManager.persist(tut1);

		Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
		entityManager.persist(tut2);

		Tutorial foundTutorial = repository.findById(tut2.getId()).get();

		assertThat(foundTutorial).isEqualTo(tut2);
	}

	@Test
	public void shouldFindPublishedTutorials() {
		Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
		entityManager.persist(tut1);

		Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
		entityManager.persist(tut2);

		Tutorial tut3 = new Tutorial("Tut#3", "Desc#3", true);
		entityManager.persist(tut3);

		Iterable<Tutorial> tutorials = repository.findByPublished(true);

		assertThat(tutorials).hasSize(2).contains(tut1, tut3);
	}

	@Test
	public void shouldFindTutorialsByTitleContainingString() {
		Tutorial tut1 = new Tutorial("Spring Boot Tut#1", "Desc#1", true);
		entityManager.persist(tut1);

		Tutorial tut2 = new Tutorial("Java Tut#2", "Desc#2", false);
		entityManager.persist(tut2);

		Tutorial tut3 = new Tutorial("Spring Data JPA Tut#3", "Desc#3", true);
		entityManager.persist(tut3);

		Iterable<Tutorial> tutorials = repository.findByTitleContaining("ring");

		assertThat(tutorials).hasSize(2).contains(tut1, tut3);
	}

	@Test
	public void shouldUpdateTutorialById() {
		Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
		entityManager.persist(tut1);

		Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
		entityManager.persist(tut2);

		Tutorial updatedTut = new Tutorial("updated Tut#2", "updated Desc#2", true);

		Tutorial tut = repository.findById(tut2.getId()).get();
		tut.setTitle(updatedTut.getTitle());
		tut.setDescription(updatedTut.getDescription());
		tut.setPublished(updatedTut.isPublished());
		repository.save(tut);

		Tutorial checkTut = repository.findById(tut2.getId()).get();

		assertThat(checkTut.getId()).isEqualTo(tut2.getId());
		assertThat(checkTut.getTitle()).isEqualTo(updatedTut.getTitle());
		assertThat(checkTut.getDescription()).isEqualTo(updatedTut.getDescription());
		assertThat(checkTut.isPublished()).isEqualTo(updatedTut.isPublished());
	}

	@Test
	public void shouldDeleteTutorialById() {
		Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
		entityManager.persist(tut1);

		Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
		entityManager.persist(tut2);

		Tutorial tut3 = new Tutorial("Tut#3", "Desc#3", true);
		entityManager.persist(tut3);

		repository.deleteById(tut2.getId());

		Iterable<Tutorial> tutorials = repository.findAll();

		assertThat(tutorials).hasSize(2).contains(tut1, tut3);
	}

	@Test
	public void shouldDeleteAllTutorials() {
		entityManager.persist(new Tutorial("Tut#1", "Desc#1", true));
		entityManager.persist(new Tutorial("Tut#2", "Desc#2", false));

		repository.deleteAll();

		assertThat(repository.findAll()).isEmpty();
	}

}
