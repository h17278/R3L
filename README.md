<h2>Bienvenue sur HEIvents</h2>

<h2> Le but de ce site est de lister de manière lisible et pratique les futurs événements des associations HEI </h2>

<p> A l'ouverture du Tomcat, il y a deux onglets. 
Un pour lister les associations et l'autre pour lister les événements.</p>
<p> On peut cliquer sur "plus d'infos" sur chacun des événements si on est connecté.</p>

<p> On peut aussi se connecter avec un bouton en haut à droite qui change en déconnexion si une session est déjà ouverte.</p>
<p> Si l'utilisateur connecté est président d'une association, il aura accès à l'onglet Ajout d'événement et gestion utilisateur.</p>

<h2> Accès président </h2>

<p> Les présidents et les associations qu'ils gèrent sont au pied de ce document.</p>

<br>

<p> Un président peut ajouter un événement de son association seulement. Il peut accéder facilement à la suppression et à la modification des événements de son association. Les autres présidents peuvent y accéder en tapant le bon URL.</p>

<p> La gestion utilisateurs est un onglet des présidents. Il sert à modifier les accès des autres utilisateurs (président ou non) et à supprimer tous les utilisateurs sauf soi-même. </p>

<h2> Base de données et descriptions des utilisateurs </h2>

<h3> Tous les mots de passe sont : mdp</h3>

<ul>
    <li>brieuc est membre du Raid</li>
    <li>gautrob est président du Raid</li>
    <li>guerissologue est membre de Saturne</li>
    <li>guigui est membre du Raid</li>
    <li>hugo est président de Saturne</li>
    <li>iktro est président HeirForce</li>
    <li>palpal est président de Athle HEI</li>
    <li>polo est membre de Athle Hei</li>
    <li>samos est membre du Raid</li>
</ul>
<p> D'autres membres peuvent être créés dans l'onglet utilisateur, ils peuvent être créés avec ou sans autorisation pour les présidents</p>

<p> Pour la base de données, il suffit de lancer creationTables puis populateTables dans une base de données nommé eventlist </p>

<p> Pour relancer la base de données, il suffit d'ajouter ces lignes avant le creationTables.sql: </p>
<p>
DROP TABLE `event`; <br>
DROP TABLE `utilisateur`; <br>
DROP TABLE `club`;
</p>
