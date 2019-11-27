package hei.devweb.projetit.entities;

import java.util.Comparator;

public enum UtilisateurSortable {
    ID_UTILISATEUR(Comparator.comparing(Utilisateur::getIdutilisateur)),
    PSEUDO(Comparator.comparing(Utilisateur::getPseudo)),
    MOT_DE_PASSE(Comparator.comparing(Utilisateur::getMotdepasse)),
    MAIL(Comparator.comparing(Utilisateur::getMail)),
    PRESIDENT(Comparator.comparing(Utilisateur::getPresident)),
    ID_CLUB(Comparator.comparing(Utilisateur::getClub));

    private Comparator<Utilisateur> comparator;

    UtilisateurSortable(Comparator<Utilisateur> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Utilisateur> getComparator() {
        return comparator;
    }


}
