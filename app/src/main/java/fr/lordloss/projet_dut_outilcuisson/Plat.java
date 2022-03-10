package fr.lordloss.projet_dut_outilcuisson;

public class Plat {

    /* Définition des différents attribut de l'objet Plat */
    private String nom;
    private String duree;
    private int deg;

    /**
     * Constructeur de l'objet plat qui est définit par : un nom, une duree, un degre de temperature
     * @param nom : le nom est un string qui définit l'appelation du plat, c'est un String
     * @param duree : la durée correspond à la durée de cuisson du plat, c'est un String
     * @param deg : le degrès correspond à la température de cuisson d'un plat, c'est un int
     */
    public Plat(String nom, String duree, int deg) {
        this.nom = nom;
        this.duree = duree;
        this.deg = deg;
    }

    /**
     * Permet de récupérer le nom de l'objet Plat
     * @return : retourne un String qui correspond à l'appelation de l'objet plat
     */
    public String getNom() {
        return nom;
    }

    /**
     * Méthode qui permet de modifier le nom d'appellation de l'objet Plat
     * @param nom : élément d'appellation que l'on souhaite modifier
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Permet de récupérer la durée de cuisson de l'objet Plat
     * @return : retourne un String qui correspond à la duréé de cuisson de l'objet Plat
     */
    public String getDuree() {
        return duree;
    }

    /**
     * Méthode qui permet de modifier la duree de cuisson de l'objet Plat
     * @param duree : élément duree que l'on souhaite modifier
     */
    public void setDuree(String duree) {
        this.duree = duree;
    }

    /**
     * Permet de récupérer le degrès de température de cuisson de l'objet Plat
     * @return : retourne un int qui correspond à au degrès de température de cuisson de l'objet Plat
     */
    public int getDeg() {
        return deg;
    }

    /**
     * Méthode qui permet de modifier le degrès de température de cuisson de l'objet Plat
     * @param deg : élément degrès de température que l'on souhaite modifier
     */
    public void setDeg(int deg) {
        this.deg = deg;
    }

    /**
     * Méthode qui permet de comparer les nom de plats
     * Utilisé afin que l'on ne puisse pas inscrire plusieurs fois le meme plat
     * @param o On prend le nouveau plat saisie pour tester
     * @return on return un boolean true si ils sont équivalent sinon false
     */
    public boolean equals(Plat o) {
        return this.nom.equals(o.getNom());
    }
}

