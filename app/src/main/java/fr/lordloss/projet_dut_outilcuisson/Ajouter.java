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

    private Button btnEffacer;
    private Button btnValider;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

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

    @SuppressLint("ResourceAsColor")
    public void AfficherErreur() {
        AlertDialog alert = new AlertDialog.Builder(getContext())
                .setTitle(R.string.erreur_de_saisie)
                .setMessage(R.string.alert_erreur_de_saisie)
                .setNeutralButton(R.string.retourAlertDialog, null)
                .show();

        alert.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(R.color.color_titre_liste);



    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        String nom = nomPlat.getText().toString();
        String temp = temperature.getText().toString();
        String dure;

        if(view.getId() == R.id.btnValider) {
            if(temp.isEmpty() || nom.isEmpty()) {
                AfficherErreur();
            } else if(Integer.parseInt(temp) > 300 || Integer.parseInt(temp) <= 0) {
                AfficherErreur();
            } else if(nomPlat.getText().toString().contains("|")) {
                AfficherErreur();
            } else if (duree.getCurrentHour() > 9 || (duree.getCurrentHour() == 0 && duree.getCurrentMinute() == 0)) {
                AfficherErreur();
            } else {
                if(exist(nom)) {
                    AlertDialog salut = new AlertDialog.Builder(getContext())
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

    private boolean exist(String nom) {
        ArrayList<Plat> liste = Afficher.list;
        for (Plat plat : liste ) {
            if(plat.getNom().equals(nom)) {
                return true;
            }
        }
        return false;
    }

}