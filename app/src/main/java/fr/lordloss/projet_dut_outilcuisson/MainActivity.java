package fr.lordloss.projet_dut_outilcuisson;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import fr.lordloss.projet_dut_outilcuisson.adapter.PlatListeAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String NOM_FICHIER = "bd.txt";
    private static final Logger logger = Logger.getLogger(MainActivity.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * on récupère un accès sur le ViewPager défini dans la vue
         * ainsi que sur le TabLayout qui gèrera les onglets
         */
        ViewPager2 gestionnairePagination = findViewById(R.id.activity_main_viewpager);
        TabLayout gestionnaireOnglet = findViewById(R.id.tab_layout);

        /*
         * On associe au ViewPager un adaptateur (c'est lui qui organise le défilement
         * entre les fragments à afficher
         */
        gestionnairePagination.setAdapter(new AdaptateurPage(this));

        /*
         * On regroupe dans un tableau les intitulés des boutons d'onglet
         */
        String[] titreOnglet = {getString(R.string.afficher), getString(R.string.ajouter)};

        /*
         * On crée une instance de type TabLayoutMediator qui fera le lien entre le gestionnaire
         * de pagination et le gestionnaire des onglets. La méthode onConfigureTab permet de
         * préciser quel intitulé de bouton d'onglets correspond à tel ou tel onglet, selon
         * la position de celui-ci. L'instance TabLayoutMediator est attachée à l'activité courante
         * Cette syntaxe s'inspire du kotlin
         */
        new TabLayoutMediator(gestionnaireOnglet, gestionnairePagination,
                ((tab, position) -> tab.setText(titreOnglet[position])
                )).attach();
    }

    /**
     * méthode qui permet d'arreter le fonctionnement de l'application lorsqu'on la quitte
     */
    @Override
    public void onStop() {
        super.onStop();
        enregistre();
    }

    /**
     * méthode qui se lance a l'ouverture de l'application
     */
    @Override
    public void onStart() {
        super.onStart();
        chargementDonnee();
    }

    /**
     *
     */
    public void enregistre() {
        String ligne;
        try {
            FileOutputStream fichier = openFileOutput(NOM_FICHIER, Context.MODE_PRIVATE);
            ArrayList<Plat> list = Afficher.list;

            for ( Plat plat : list ) {
                ligne = plat.getNom() + "|" + plat.getDuree() + "|" + plat.getDeg();
                fichier.write(ligne.getBytes());
                fichier.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void chargementDonnee() {
        String plat;
        ArrayList<Plat> listPlat = new ArrayList<>();

        try {
            InputStreamReader fichier = new InputStreamReader(openFileInput(NOM_FICHIER));
            BufferedReader fichiertexte = new BufferedReader(fichier);
            while ((plat = fichiertexte.readLine()) != null) {
                listPlat.add(getPlat(plat));
            }
        } catch (FileNotFoundException e) {
            logger.severe(e.getMessage());
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
        Afficher.list = listPlat;
    }

    private Plat getPlat(String plat) {
        String REGEX = "\\|";
        Pattern pattern = Pattern.compile(REGEX);
        String[] result = pattern.split(plat);
        logger.severe(Arrays.toString(result));
        return new Plat(result[0], result[1], Integer.parseInt(result[2].toString()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aide:
                aide();
                break;
            case R.id.reinitialiser:
                confirmation();
                //TODO taf
                break;
            case R.id.annule:
                break;
        }

        return super.onOptionsItemSelected(item);
    }



    public void confirmation() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.attention)
                .setMessage(R.string.contenu_attention)
                .setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Afficher.list.clear();
                        enregistre();
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    }
                })
                .setNegativeButton(R.string.non, null)
                .show();
    }

    public void aide() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.attention)
                .setMessage(R.string.message_aide)
                .setNeutralButton(R.string.ferme, null)
                .show();

    }
}