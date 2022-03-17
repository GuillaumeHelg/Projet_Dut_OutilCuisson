package fr.lordloss.projet_dut_outilcuisson;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;


import java.util.ArrayList;

/**
 * classe qui gere le fragment Ajouter
 */
public class Ajouter extends Fragment implements View.OnClickListener {

    /* Bouton xml liés au fragment ajouter */
    private Button btnEffacer;
    private Button btnValider;

    /* EditText et TimePicker qui sont liés aux données entré par l'utilisateur */
    private TimePicker duree;
    private EditText nomPlat;
    private EditText temperature;


    public Ajouter() {
        // Required empty public constructor
    }

    /**
     * Cette méthode est une factory : son role est de creer une nouvelle instance du Fragment
     * de type Ajouter
     * @return : On retourne une nouvelle instance de type Ajouter
     */
    public static Ajouter newInstance() {
        Ajouter fragment = new Ajouter();
        return fragment;
    }

    /**
     * méthode qui permet de créer la vue
     * @param savedInstanceState :
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * Méthode qui va permettre l'initialisation des toutes les variables liées au xml
     * @param inflater :
     * @param container :
     * @param savedInstanceState :
     * @return :
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vueDuFragment = inflater.inflate(R.layout.fragment_ajouter, container, false);

        vueDuFragment.findViewById(R.id.btnValider).setOnClickListener(this);
        vueDuFragment.findViewById(R.id.btnEffacer).setOnClickListener(this);
        duree = vueDuFragment.findViewById(R.id.TPDureeCuisson);
        duree.setIs24HourView(true);
        duree.setCurrentHour(0);
        duree.setCurrentMinute(5);
        nomPlat = vueDuFragment.findViewById(R.id.EditNomPlat);
        temperature = vueDuFragment.findViewById(R.id.EditTemperature);

        // Inflate the layout for this fragment
        return vueDuFragment;
    }

    /**
     * méthode qui affiche une popup d'erreur lorsque qu'il y'a une erreur de saisie
     */
    @SuppressLint("ResourceAsColor")
    public void afficherErreur() {
        AlertDialog alert = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.erreur_de_saisie)
                .setMessage(R.string.alert_erreur_de_saisie)
                .setNeutralButton(R.string.retourAlertDialog, null)
                .show();
        alert.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(R.color.color_titre_liste);
    }

    /**
     * Méthode onClick qui teste si les données entrées sont bonnes lorsque l'on clique sur le bouton
     * et qui permet de soit valider l'ajout des données soit d'effacer les données entrées
     * @param view :
     */
    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        String nom = nomPlat.getText().toString();
        String temp = temperature.getText().toString();
        String dure;

        if(view.getId() == R.id.btnValider) {
            if(temp.isEmpty() || nom.isEmpty()) {
                afficherErreur();
            } else if(Integer.parseInt(temp) > 300 || Integer.parseInt(temp) <= 0) {
                afficherErreur();
            } else if(nomPlat.getText().toString().contains("|")) {
                afficherErreur();
            } else if (duree.getCurrentHour() > 9 || (duree.getCurrentHour() == 0 && duree.getCurrentMinute() == 0)) {
                afficherErreur();
            } else {
                if(exist(nom)) {
                    AlertDialog salut = new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.erreur_de_saisie)
                            .setMessage(R.string.alert_erreur_plat_existant)
                            .setNeutralButton(R.string.ferme, null)
                            .show();
                    salut.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(R.color.color_titre_liste);
                } else {
                    dure  = duree.getCurrentHour() + "h" + (duree.getCurrentMinute() <= 9 ? "0" + duree.getCurrentMinute() : duree.getCurrentMinute());
                    Afficher.list.add(new Plat(nom, dure, Integer.parseInt(temp)));
                    Toast.makeText(getContext(), getString(R.string.toast_ajout, nomPlat.getText()), Toast.LENGTH_LONG).show();
                }
            }
        } else if (view.getId() == R.id.btnEffacer) {
            temperature.setText("");
            nomPlat.setText("");
            duree.setCurrentHour(0);
            duree.setCurrentMinute(0);
        }
    }

    public static boolean exist(String nom) {
        ArrayList<Plat> liste = Afficher.list;
        for (Plat plat : liste ) {
            if(plat.getNom().trim().equals(nom.trim())) {
                return true;
            }
        }
        return false;
    }

}