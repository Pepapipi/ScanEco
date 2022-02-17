package com.example.scaneco.recherchesansscan;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scaneco.DoneesProduit;
import com.example.scaneco.Produit;
import com.example.scaneco.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder> {


    List<Produit> lpdt;

    public List<List<String>> listeEmballageChaqueProduit = new ArrayList<>();
    RecyclerViewClickListner recyclerViewClickListner;
    private DoneesProduit m_donnees;


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Button boutonPrecedent;
        private Button boutonSuivant;
        private ImageView _imageViewPoubelle1;
        private ImageView _imageViewPoubelle2;
        private ImageView _imageViewPoubelle3;
        TextView m_nomPdt;
        TextView m_marque;
        ImageView m_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            m_marque = itemView.findViewById(R.id.text);
            m_nomPdt = itemView.findViewById(R.id.description);
            m_image = itemView.findViewById(R.id.imageView);
            _imageViewPoubelle1 = itemView.findViewById(R.id.poubelle1);
            _imageViewPoubelle2 = itemView.findViewById(R.id.poubelle2);
            _imageViewPoubelle3 = itemView.findViewById(R.id.poubelle3);
            boutonPrecedent = (Button) itemView.findViewById(R.id.boutonPagePrecedente);
            boutonSuivant = (Button) itemView.findViewById(R.id.boutonPageSuivante);
            itemView.setOnClickListener(this);

        }

        public void setData(String nomPdt, String marquePdt, Drawable img, ImageView img1,ImageView img2, ImageView img3) {
         m_marque.setText(marquePdt);
         m_nomPdt.setText(nomPdt);
         m_image.setImageDrawable(img);
         _imageViewPoubelle1 = img1;
         _imageViewPoubelle2 = img2;
         _imageViewPoubelle3 = img3;
        }

        @Override
        public void onClick(View view) {

            recyclerViewClickListner.onClick(view, getAdapterPosition());
        }
    }

    public ProgramAdapter(List<Produit> p, RecyclerViewClickListner listen)
    {
        this.lpdt = p;
        this.recyclerViewClickListner = listen;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        if(viewType == R.layout.single_item){
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item, parent, false);
        }

        else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_arrow, parent, false);
        }

        ViewHolder views = null;
        try
        {
            views = new ViewHolder(itemView);
        }
        catch(Exception ignored)
        { }
        return Objects.requireNonNull(views);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position==lpdt.size())
        {
            //Clique sur le bouton précedent
            holder.boutonPrecedent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Bouton précédent cliqué", Toast.LENGTH_SHORT).show();
                }
            });
            //Clique sur le bouton Suivant
            holder.boutonSuivant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Bouton suivant cliqué", Toast.LENGTH_SHORT).show();
                }
            });

        }
        else {

            //Initialisation des listes
            m_donnees = new DoneesProduit();
            m_donnees.initialisationDesListes();
            List<String> listeEmballageA = new ArrayList<>();
            try {
                m_donnees.afficherPoubelleSansTexte(lpdt.get(position), holder._imageViewPoubelle1, holder._imageViewPoubelle2, holder._imageViewPoubelle3);

                String nomPdt = lpdt.get(position).getNom();
                String marquePdt = lpdt.get(position).getMarque();
                lpdt.get(position).loadImage();
                Drawable imagePdt = lpdt.get(position).getImage();
                listeEmballageA.add(m_donnees.text1);
                listeEmballageA.add(m_donnees.text2);
                listeEmballageA.add(m_donnees.text3);
                listeEmballageChaqueProduit.add(listeEmballageA);

                holder.setData(nomPdt, marquePdt, imagePdt, holder._imageViewPoubelle1, holder._imageViewPoubelle2, holder._imageViewPoubelle3);
            } catch (Exception ignored) {}

        }
    }

    @Override
    public int getItemCount() {
        return lpdt.size() +1 ;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == lpdt.size()) ? R.layout.choose_arrow : R.layout.single_item;
    }

}
