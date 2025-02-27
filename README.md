# TP-Noté-Mathieu
TP test\
TP - TDD\
Fabien Le Bronnec\
─\
Présentation\
Il s’agit de la réalisation d’une API Rest de gestion d’une bibliothèque en utilisant la
méthodologie TDD.\
Règles générales\
L’API doit permettre de gérer le fond disponible, les abonnées et l’état des emprunts. Ces
données sont persistées dans une base de données. Il sera aussi nécessaire d’envoyer des
mails. Il existe aussi un web service de référencement pour faciliter la création des livres
dans l’application.\
Règles fonctionnelles\
Consultation et mise à jour du fond\
CRUD des livres du fond de la bibliothèque.\
Les livres sont composés des informations suivantes :\
● Isbn\
● Titre\
● Auteur\
● Editeur\
● Format\
● S’il est disponible ou non (voir plus loin)\
Les formats possibles sont :\
● Poche\
● Broché\
● Grand format (BD, etc…)\
On doit pouvoir ajouter, modifier, supprimer un livre.\
Rechercher par isbn, titre, auteur.\
2\
Autres règles\
● Un isbn de livre doit être valide\
● Un livre doit obligatoirement avoir tous les champs renseignés avant d’être
enregistré en base\
● Si il manque des informations, elles doivent être récupérées auprès du web service,
il prend en paramètre un isbn\
● Par simplicité, chaque livre est présent en un seul exemplaire dans le fond.\
Base adhérent\
Un adhérent est composé des informations suivantes :\
● Code adhérent\
● Nom\
● Prénom\
● Date de naissance\
● Civilité\
Réservations\
Il est possible de faire une requête de réservation pour un adhérent.\
Une réservation a une date limite, d’un maximum de 4 mois au jour du dépôt de la
réservation.\
On peut mettre fin à la réservation.\
Un adhérent ne peut avoir plus de 3 réservations ouvertes simultanées.\
Gestion\
Il est possible d’obtenir la liste des réservations ouvertes.\
On peut aussi consulter l’’historique des réservations pour un adhérent.\
Il est possible de demander un mail de rappel pour toutes les réservations qui ont dépassé
leur date.\
Chaque adhérent ne reçoit qu’un seul mail avec la liste de ses réservations dépassées s’il y
en a plusieurs.
