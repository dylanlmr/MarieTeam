package fr.xkgd.marieteam.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.xkgd.marieteam.R;

public class TraverseeViewHolder extends RecyclerView.ViewHolder {

    final TextView textViewTraversee;
    private final TextView textViewDebut;
    private final TextView textViewFin;
    private final TextView textViewEtatTraversee;
    private final TextView textViewEtatMer;
    private final TextView textViewCommentaire;

    public TraverseeViewHolder(@NonNull View itemView, final OnItemClickListener itemClickListener) {
        super(itemView);

        textViewTraversee = itemView.findViewById(R.id.textViewTraversee);
        textViewDebut = itemView.findViewById(R.id.textViewDebut);
        textViewFin = itemView.findViewById(R.id.textViewFin);
        textViewEtatTraversee = itemView.findViewById(R.id.textViewEtatTraversee);
        textViewEtatMer = itemView.findViewById(R.id.textViewEtatMer);
        textViewCommentaire = itemView.findViewById(R.id.textViewCommentaire);

        itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener.onItemClick(position);
                }
            }
        });
    }

    /**
     * Bind les données à la vue
     * @param traversee l'id de la traversée
     * @param debut la date de début
     * @param fin la date de fin
     * @param etatTraversee l'état de la traversée
     * @param etatMer l'état de la mer
     * @param commentaire le commentaire
     */
    public void bind(String traversee, String debut, String fin, String etatTraversee, String etatMer, String commentaire) {
        textViewTraversee.setText(traversee);
        textViewDebut.setText(debut);
        textViewFin.setText(fin);
        textViewEtatTraversee.setText(etatTraversee);
        textViewEtatMer.setText(etatMer);
        textViewCommentaire.setText(commentaire);
    }
}