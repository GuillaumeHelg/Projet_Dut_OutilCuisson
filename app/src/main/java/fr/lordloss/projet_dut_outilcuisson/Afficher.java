package fr.lordloss.projet_dut_outilcuisson;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
import android.widget.Toast;


import java.util.ArrayList;
import java.util.regex.Pattern;

import fr.lordloss.projet_dut_outilcuisson.adapter.PlatListeAdapter;

/**
 * Classe qui gère le fragment afficher
 */
public class Afficher extends Fragment implements View.OnClickListener {

    /* Liste plat dans le xml */
    private  ListView listePlat;

    /* composants du xml qui doivent être modifiés */
    private TimePicker duree;
    private EditText nomPlat;
    private EditText temperature;


    /* Tableau de plats utilisé pour la listeView dans le fragment afficher mais aussi
     * dans le fragment Ajouter */
    public static ArrayList<Plat> list = new ArrayList<>();

    /* Adapteur de la liste */
    @SuppressLint("StaticFieldLeak")
    public static PlatListeAdapter adapter;

    /* Pour la barre de recherche */
    private EditText editRecherche;

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
     * Methode qui va permettrent de liés les éléments du xml à la partie java pour la partie dynamique
     * de l'application
     * @param inflater : permet d'instancier un fichier de mise en page xml
     * @param container : Layout
     * @param savedInstanceState :
     * @return : retourne une vue
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vueDuFragment = inflater.inflate(R.layout.fragment_afficher, container, false);
        listePlat = vueDuFragment.findViewById(R.id.liste_plat);

        editRecherche = (EditText) vueDuFragment.findViewById(R.id.plat_recherche);
        vueDuFragment.findViewById(R.id.btn_recherche).setOnClickListener(this);

        miseAjour();

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
    @SuppressLint("NonConstantResourceId")
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

    /**
     *
     * @param position
     */
    private void modfier(int position) {
        String temps;
        Plat plat = list.get(position);
        String regex = "h";

        Pattern pattern = Pattern.compile(regex);
        String[] result = pattern.split(plat.getDuree());

        final View boiteAjoutArticle = getLayoutInflater().inflate(R.layout.modifcation, null);

        duree = boiteAjoutArticle.findViewById(R.id.modifDureeCuisson);
        nomPlat = boiteAjoutArticle.findViewById(R.id.modifNomPlat);
        temperature = boiteAjoutArticle.findViewById(R.id.modifTemperature);

        new AlertDialog.Builder(getActivity())
                .setView(boiteAjoutArticle)
                .setNeutralButton(R.string.retourAlertDialog, null)
                .setPositiveButton(R.string.modifier, new DialogInterface.OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String nom = nomPlat.getText().toString();
                        String temp = temperature.getText().toString();
                        String dure;

                        if (temp.isEmpty() || nom.isEmpty()) {
                            afficherLesErreur();
                        } else if (Integer.parseInt(temp) > 300 || Integer.parseInt(temp) <= 0) {
                            afficherLesErreur();
                        } else if (nomPlat.getText().toString().contains("|")) {
                            afficherLesErreur();
                        } else if (duree.getCurrentHour() > 9 || (duree.getCurrentHour() == 0 && duree.getCurrentMinute() == 0)) {
                            afficherLesErreur();
                        } else {
                            dure = duree.getCurrentHour() + "h" + (duree.getCurrentMinute() <= 9 ? "0" + duree.getCurrentMinute() : duree.getCurrentMinute());
                            plat.setNom(nom);
                            plat.setDuree(dure);
                            plat.setDeg(Integer.parseInt(temp));
                            Toast.makeText(getContext(), getString(R.string.toast_modif, nomPlat.getText()), Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .show();
        miseAjour();

        nomPlat.setText(String.valueOf(plat.getNom()));
        temperature.setText(String.valueOf(plat.getDeg()));
        duree.setIs24HourView(true);
        duree.setCurrentHour(Integer.parseInt(result[0]));
        duree.setCurrentMinute(Integer.parseInt(result[1]));
    }



    public void miseAjour() {
        adapter = new PlatListeAdapter(getContext(), R.layout.liste_item, list);
        listePlat.setAdapter(adapter);
    }


    private void supprimer(int position) {
        list.remove(position);
        miseAjour();
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
        new AlertDialog.Builder(requireContext())
            .setTitle(R.string.titreAlertDialog)
            .setMessage(getString(R.string.equivalentThermostatText, plat.getNom(), plat.getDeg(), thermostat(plat.getDeg())))
            .setNeutralButton(R.string.retourAlertDialog, null)
            .show();
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        String platRecherche = editRecherche.getText().toString();
        boolean trouvee = false;
        if (view.getId() == R.id.btn_recherche) {
            for (Plat plat : list) {
                if (plat.getNom().equals(platRecherche)) {
                    Toast.makeText(getContext(), getString(R.string.plat_existe, platRecherche), Toast.LENGTH_LONG).show();
                    trouvee = true;
                    break;
                }
            }
            if (!trouvee) {
                Toast.makeText(getContext(), getString(R.string.plat_existe_pas, platRecherche), Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * méthode qui affiche une popup d'erreur lorsque qu'il y'a une erreur de saisie
     */
    @SuppressLint("ResourceAsColor")
    public void afficherLesErreur() {
        AlertDialog alert = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.erreur_de_saisie)
                .setMessage(R.string.alert_erreur_de_saisie)
                .setNeutralButton(R.string.retourAlertDialog, null)
                .show();

        alert.getButton(android.app.AlertDialog.BUTTON_NEUTRAL).setTextColor(R.color.color_titre_liste);
    }
}