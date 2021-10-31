## Exemple Spring Boot @DataJpaTest pour le test unitaire
De nos jours, les tests unitaires sont si importants dans le développement de logiciels, et Spring Framework fournit également des annotations `@DataJpaTest` pour simplifier l'écriture des tests pour le référentiel JPA. <br/>
Dans ce projet, nous allons voir comment appliquer `@DataJpaTest` dans notre project Spring Boot avec `TestEntityManager`.

### Prérequis
---
Pour ce tutoriel, vous auriez besoin des spécifications suivantes :
- Spring Boot v2.0+
- JDK v1.8+
- Maven 3+ ou Gradle 4+ - outil de construction
- Tout IDE prenant en charge Java et Spring Boot (Spring Tool Suite (STS), IntelliJ, VSC, NetBeans, etc.)

### Annotation 
---
`@DataJpaTest` est l'annotation prise en charge par Spring pour un test JPA qui se concentre uniquement sur les composants JPA.

Par défaut, les tests annotés avec @DataJpaTestsont transactionnels et sont annulés à la fin de chaque test. 
Si vous ne le souhaitez pas, vous pouvez désactiver la gestion des transactions pour un test ou pour 
toute la classe en utilisant l'annotation `@Transactional`.
```
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class YourNonTransactionalTests {

}
```

La base de données embarquée en mémoire (comme la base de données H2 dans cet exemple) 
fonctionne généralement bien pour les tests, elle est rapide et ne nécessite aucune installation. 
Cependant, nous pouvons configurer pour une vraie base de données avec l'annotation `@AutoConfigureTestDatabase` :
```
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
class YourRepositoryTests {

}
```

Si vous utilisez JUnit 4, vous devez ajouter `@RunWith(SpringRunner.class)` au test :
```
@RunWith(SpringRunner.class)
@DataJpaTest
class YourRepositoryTests {

}
```

**TestEntityManager**
Le but de l'`EntityManager` est d'interagir avec le contexte de persistance. Spring Data JPA vous extrait de EntityManager via les interfaces de référentiel. Et TestEntityManager nous permet d'utiliser EntityManager dans les tests.

### Dependances Maven
---
Dans ce tuto nous allons utiliser les dependances Maven suivants:
- **Spring Web** - Pour inclure Spring MVC et utilise le tomcat comme conteneur intégré par défaut.
- **Spring Data JPA** - API de persistance Java et Hibernate
- **Spring Boot DevTools** - dépendance pour les rechargements automatiques ou le rechargement en direct des applications.
- **H2 database** - H2 est une base de données Java légère et open source. Il peut être embarqué dans des applications Java ou exécuté en mode client-serveur.

### Exigences fonctionnelles
---
Les exigences fonctionnelles permettent de satisfaire les besoins fonctionnels (metier) de l'entreprise.
L'application doit permettre de :
- Tester les opérations CRUD et des méthodes personnalisés de l'entité `tutoriel` :
	* Ne devrait trouver aucun tutoriel si le référentiel est vide
	* Devrait stocker un tutoriel
	* Devrait trouver tous les tutoriels
	* Devrait trouver le tutoriel par identifiant
	* Devrait trouver des tutoriels publiés
	* Devrait trouver des tutoriels par titre contenant une chaîne
	* Devrait mettre à jour le tutoriel par identifiant
	* Devrait supprimer le tutoriel par identifiant
	* Devrait supprimer tous les tutoriels

### Exécuter le test unitaire
---
Pour tester le test unitaire, lancer l'application :
* Première commande d'exécution : `mvn clean install`.
* Ensuite, lancez Test : `mvn test`
<br/><br/>
![mvn test](https://user-images.githubusercontent.com/75081354/139573681-14285bc0-f2cd-4a7e-ab9b-29267d2759c0.PNG)

– Ou vous pouvez également exécuter le projet Spring Boot avec le mode JUnit Test.
– Maintenant, voyez le résultat Junit comme suit :
<br/><br/>
![run test](https://user-images.githubusercontent.com/75081354/139573691-8e09c8ac-b215-4c01-a59c-69ded595c72b.PNG)

### Conclusion
---
Aujourd'hui, nous avons créé Spring Boot Test pour le référentiel JPA avec la base de données H2 en utilisant `@DataJPATest` et `TestEntityManager` avec la base de données H2. Nous exécutons également des tests unitaires pour de nombreuses opérations CRUD et méthodes de recherche personnalisées.
