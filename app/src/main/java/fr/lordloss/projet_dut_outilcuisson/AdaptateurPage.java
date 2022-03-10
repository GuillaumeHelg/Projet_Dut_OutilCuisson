package fr.lordloss.projet_dut_outilcuisson;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AdaptateurPage extends FragmentStateAdapter {

    /** Nombre de fragments géré par cet adaptateur */
    private static final int NB_FRAGMENT = 2;

    /**
     * Constructeur de base
     * @param activite : activité qui contient le ViewPager qui gerera les fragments
     */
    public AdaptateurPage(FragmentActivity activite) {
        super(activite);
    }

    /**
     * Méthode ou on associe le viewpager à un adaptateur qui devra afficher successivement
     * un fragment de type : Afficher et Ajouter
     * C'est dans cette méthode que l'on décide dans quel ordre sont affichés les fragments,
     * et quels fragment doit précisement etre affiché
     * @param position : position de l'onglet sur lequel l'utilisateur est présent
     * @return Si le fragment existe on appelle la methode factory de ce fragment sinon null
     */
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return Afficher.newInstance();
            case 1:
                return Ajouter.newInstance();
            default:
                return null;
        }
    }


    /**
     *
     * @return : renvoie le nombre de fragment
     */
    @Override
    public int getItemCount() {
        return NB_FRAGMENT;
    }
}
