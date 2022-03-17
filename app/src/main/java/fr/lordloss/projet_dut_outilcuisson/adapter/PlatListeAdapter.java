package fr.lordloss.projet_dut_outilcuisson.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import fr.lordloss.projet_dut_outilcuisson.Plat;
import fr.lordloss.projet_dut_outilcuisson.R;

public class PlatListeAdapter extends ArrayAdapter<Plat> {

    /* contexte de l'application */
    private Context context;

    /**
     * Méthode qui permet de modifier l'affichage classque des listes
     * @param context
     * @param liste_item
     * @param listePlat : tableau qui contient tout les plats de l'application
     */
    public PlatListeAdapter(Context context, int liste_item, ArrayList<Plat> listePlat) {
        super(context, liste_item, listePlat);
        this.context = context;
    }


    /**
     * Méthode qui permet de modifié la vue de la listView de l'application
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Plat plat = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.liste_item, parent,false);
        }
        TextView nom = convertView.findViewById(R.id.nomPlat);
        TextView duree = convertView.findViewById(R.id.duree);
        TextView deg = convertView.findViewById(R.id.degres);

        nom.setText(plat.getNom());
        duree.setText("|    "+ plat.getDuree() + "    |");
        deg.setText(plat.getDeg() + "");

        return convertView;
    }
}
