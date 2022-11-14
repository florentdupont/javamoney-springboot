# SpringBoot MonetaryAmount

Exemple de mise en place du type Monetary Amount dans une application SpringBoot.
Plusieurs choses à noter : 
- Jackson est configuré avec un Serializer et un désérialiseur pour construire automatiquement le type MonetaryAmount vers l'objet JSON (et inversement) sans avoir à mapper au préalable vers un DTO.
- Hibernate est configuré pour mapper automatiquement le Type MoneraryAmount en BDD.

Il est cependant nécessaire de spécifier le nom des colonnes:

```java
@Columns(columns = {@Column(name = "montant_ttc_devise"),
                    @Column(name = "montant_ttc_valeur")})
```

par défaut, la devise serait mappée avec un varchar(255) et la valeur en numeric(19,2) (le mapping par défaut d'Hibernate pour les bigdecimal)

Il est préférable de préciser la génération des colonnes :
```java
@Columns(columns = {@Column(name = "montant_ttc_devise", length = 3),
@Column(name = "montant_ttc_valeur", columnDefinition = "numeric(11,2)")})
```

réduire la taille de la devise à 3 caractères
Indiquer la taille max du champs et notamment faire matcher le fractionDigits attendu de la monnaie avec le scale sur la définition du numeric.

L'exemple démontre aussi la possibilité d'utiliser une Currency déjà intégrée au JDK (l'euro) et de l'utiliser avec un nombre plus important de FractionalDigits.
Voir MoneyUtils.amount(amount, fractionDigits)

Autre subtilité : arrondir le résultat avant la persistance ou avant la sérialisation.