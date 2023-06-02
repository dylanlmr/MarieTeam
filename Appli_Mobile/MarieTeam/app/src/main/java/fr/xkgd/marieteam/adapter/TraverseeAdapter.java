package fr.xkgd.marieteam.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.xkgd.marieteam.R;
import fr.xkgd.marieteam.model.Traversee;

public class TraverseeAdapter extends RecyclerView.Adapter<TraverseeViewHolder> {

    private static final String TAG = "TraverseeAdapter";
    private List<Traversee> traversees;
    private final OnItemClickListener mlistener;

    public TraverseeAdapter(List<Traversee> mTraversees, OnItemClickListener mlistener) {
        this.traversees = mTraversees;
        this.mlistener = mlistener;
    }

    /**
     * Ajoute des traversées à la liste
     * @param traversees traversées à ajouter
     */
    public void setTraversees(List<Traversee> traversees) {
        this.traversees = traversees;
        notifyDataSetChanged();
    }

    /**
     * Ajoute une traversée modifiée à la liste
     * @param index index de la traversée à modifier
     * @param modifiedTraversee traversée modifiée
     */
    public void editTraversee(int index, Traversee modifiedTraversee) {
        if (index != -1) {
            traversees.set(index, modifiedTraversee);
            notifyItemChanged(index);
        }
    }

    @NonNull
    @Override
    public TraverseeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.traversee_recyclerview_item, parent, false);
        return new TraverseeViewHolder(view, mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull TraverseeViewHolder holder, int position) {
        Traversee current = traversees.get(position);

        if (current.getStatus() == 0) {
            holder.textViewTraversee.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.stopwatch, 0);
        } else {
            holder.textViewTraversee.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.success, 0);
        }
        holder.bind(
                "Traversée n° " + (position + 1),
                current.getStrDepart(),
                current.getStrArrivee(),
                "État traversée : " + (current.isTerminee() ? "Terminée" : "En cours"),
                "État mer : " + (current.getEtatMer() != null ? current.getEtatMer() : "Non renseigné"),
                "Commentaire : " + (current.getCommentaire() != null ? current.getCommentaire() : "Non renseigné"));
    }

    @Override
    public int getItemCount() {
        return traversees.size();
    }

    /**
     * Récupère une traversée à une position donnée
     * @param position position de la traversée
     * @return traversée à la position donnée
     */
    public Traversee getTraversee(int position) {
        return traversees.get(position);
    }
}
