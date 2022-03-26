package com.example.scaneco.recherchesansscan;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scaneco.DoneesProduit;
import com.example.scaneco.Produit;
import com.example.scaneco.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder> {


    List<Produit> lpdt;

    private List<List<String>> listeEmballageChaqueProduit = new ArrayList<>();
    RecyclerViewClickListner recyclerViewClickListner;

    public List<List<String>> getListeEmballageChaqueProduit() {
        return listeEmballageChaqueProduit;
    }

    public void setListeEmballageChaqueProduit(List<List<String>> listeEmballageChaqueProduit) {
        this.listeEmballageChaqueProduit = listeEmballageChaqueProduit;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView mImageViewPoubelle1;
        private ImageView mImageViewPoubelle2;
        private ImageView mImageViewPoubelle3;
        TextView mNomPdt;
        TextView mMarque;
        ImageView mImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mMarque = itemView.findViewById(R.id.text);
            mNomPdt = itemView.findViewById(R.id.description);
            mImage = itemView.findViewById(R.id.imageView);
            mImageViewPoubelle1 = itemView.findViewById(R.id.poubelle1);
            mImageViewPoubelle2 = itemView.findViewById(R.id.poubelle2);
            mImageViewPoubelle3 = itemView.findViewById(R.id.poubelle3);

            itemView.setOnClickListener(this);

        }

        public void setData(String nomPdt, String marquePdt, Drawable img, ImageView img1,ImageView img2, ImageView img3) {
         mMarque.setText(marquePdt);
         mNomPdt.setText(nomPdt);
         mImage.setImageDrawable(img);
         mImageViewPoubelle1 = img1;
         mImageViewPoubelle2 = img2;
         mImageViewPoubelle3 = img3;
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
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item, parent, false);
        ViewHolder views = null;
        try
        {
            views = new ViewHolder(itemView);
        }
        catch(Exception exception){
            exception.printStackTrace();
        }
        return Objects.requireNonNull(views);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            //Initialisation des listes
        DoneesProduit mDonnees = new DoneesProduit();
            mDonnees.initialisationDesListes();
            List<String> listeEmballageA = new ArrayList<>();
            try {
                mDonnees.afficherPoubelleSansTexte(lpdt.get(position), holder.mImageViewPoubelle1, holder.mImageViewPoubelle2, holder.mImageViewPoubelle3);

                String nomPdt = lpdt.get(position).getNom();
                String marquePdt = lpdt.get(position).getMarque();
                lpdt.get(position).loadImage();
                Drawable imagePdt = lpdt.get(position).getImage();
                listeEmballageA.add(mDonnees.getText1());
                listeEmballageA.add(mDonnees.getText2());
                listeEmballageA.add(mDonnees.getText3());
                getListeEmballageChaqueProduit().add(listeEmballageA);

                holder.setData(nomPdt, marquePdt, imagePdt, holder.mImageViewPoubelle1, holder.mImageViewPoubelle2, holder.mImageViewPoubelle3);
            }
            catch (InterruptedException interruptedException){
                interruptedException.printStackTrace();
                Thread.currentThread().interrupt();
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }

        }


    @Override
    public int getItemCount() {
        return lpdt.size();
    }


}
