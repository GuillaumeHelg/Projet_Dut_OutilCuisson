package fr.lordloss.projet_dut_outilcuisson;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;

import java.util.ArrayList;

import fr.lordloss.projet_dut_outilcuisson.adapter.PlatListeAdapter;

/**
 * Classe qui gère le fragment afficher
 */
public class Afficher extends Fragment {

    /* Liste plat */
    private  ListView listePlat;

    public static ArrayList<Plat> list = new ArrayList<>();

    public static PlatListeAdapter adapter;

    /**
     * Température maximale pour la cuisson
     */
    public static final int TEMPERATURE_MAX = 300;

    public Afficher() {
        // Required empty public constructor
    }

    /**
     * Cette méthode est une factory : son role est de creer une nouvelle instance du Fragment
     * de type Afficher
     * @return : On retourne une nouvelle instance de type Afficher
     */
    public static Afficher newInstance() {
        Afficher fragment = new Afficher();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return :
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vueDuFragment = inflater.inflate(R.layout.fragment_afficher, container, false);
        listePlat = vueDuFragment.findViewById(R.id.liste_plat);

//        Plat plat1 = new Plat("Frite", "0h55", 152);
//        Plat plat2 = new Plat("Poulet chaud", "0h55", 152);
//        Plat plat3 = new Plat("Petite pute au bord de l'eau", "0h55", 152);
//        Plat plat4 = new Plat("Léna Zee du port", "0h55", 152);
//
//        list.add(plat1);
//        list.add(plat2);
//        list.add(plat3);
//        list.add(plat4);

        adapter = new PlatListeAdapter(getContext(), R.layout.liste_item, list);

        listePlat.setAdapter(adapter);

        registerForContextMenu(listePlat);

        return vueDuFragment;
    }


    /**
     * Méthode invoquée automatiquement lorsque l'utilisateur active un menu contextuel
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        /*
         * on désérialise le fichier XML décriant le menu et on l'associe
         * au menu argument (celui qui a été activé)
         */
        new MenuInflater(getContext()).inflate(R.menu.menu_liste_option, menu);
    }

    /**
     * Méthode invoquée automatiquement lorsque l'utilisateur choisira une option
     * dans un menu contextuel
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo information =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.voirThermostat: alert(information.position); break;
            case R.id.supprimer: supprimer(information.position); break;
            case R.id.modifier: modfier(information.position); break;
            case R.id.annuler: break;
        }
        return (super.onContextItemSelected(item));
    }

    private void modfier(int position) {
        Ajouter.newInstance();
    }

    private void supprimer(int position) {
        list.remove(position);
        adapter = new PlatListeAdapter(getContext(), R.layout.liste_item, list);
        listePlat.setAdapter(adapter);
    }

    /**
     * Renvoie le thermostat correspondant à la temperature arguemnt
     * (celle-ci doit être inférieure à TEMPERATURE_MAX)
     * @param temperature température à convertir
     * @return  l'entier égal au thermostat ou -1 si la température est invalide
     */
    public static int thermostat(int temperature) {
        int aRenvoyer;          // valeur du thermostat à renvoyer
        if (temperature <= 0 || temperature > TEMPERATURE_MAX) {
            aRenvoyer = -1;
        } else {
            aRenvoyer = temperature / 30;
            if (temperature % 30 > 15) {
                aRenvoyer++;
            }
        }
        return aRenvoyer;
    }

    /**
     * Crée une boite de dialog qui affiche les informations sur le plat, la température de cuissons
     * le thermostat
     * @param pos Position du plat dans la liste
     */
    private void alert(int pos) {
        Plat plat = list.get(pos);
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.titreAlertDialog)
                .setMessage(getString(R.string.equivalentThermostatText, plat.getNom(), plat.getDeg(), thermostat(plat.getDeg())))
                .setNeutralButton(R.string.retourAlertDialog, null)
                .show();
    }
}