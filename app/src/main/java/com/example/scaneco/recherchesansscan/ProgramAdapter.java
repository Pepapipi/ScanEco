package com.example.scaneco.recherchesansscan;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scaneco.Produit;
import com.example.scaneco.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder> {


    List<Produit> lpdt;
    RecyclerViewClickListner recyclerViewClickListner;

    private final List<String> listeRecyclable= new ArrayList<>();
    private final String[] tabRecyclable = {"Bouteille plastique", "Etui en carton", "Brique en carton", "Canette","Bouteille en PET",
            "Bouteille en plastique", "plastic bottle","Bouteille et bouchon 100% recyclable", "Boite en métal", "Boite en carton"
            ,"Bouchon en plastique","Couvercle en métal", "Carton", "Opercule papier", "Pot en plastique", "Couvercle en plastique"};

    private final List<String> listeVerre = new ArrayList<>();
    private final String[] tabVerre = {"Verres", "Verre", "Bouteille en verre", "Bouteille verre","Pot en verre"};

    private final List<String> listeNonRecyclable = new ArrayList<>();
    private final String[] tabNonRecyclabe = {"Sachet en plastique", "Film en plastique", "Sachet plastique", "Plastique", "Barquette en plastique", "Paquet en plastique", "Emballage en plastique"};


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ImageView _imageViewPoubelle1;
        private final ImageView _imageViewPoubelle2;
        private final ImageView _imageViewPoubelle3;
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
            itemView.setOnClickListener(this);

        }

        public void setData(String nomPdt, String marquePdt, Drawable img, int img1,int img2, int img3) {
         m_marque.setText(marquePdt);
         m_nomPdt.setText(nomPdt);
         m_image.setImageDrawable(img);
         _imageViewPoubelle1.setImageResource(img1);
         _imageViewPoubelle2.setImageResource(img2);
         _imageViewPoubelle3.setImageResource(img3);

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item,parent,false);
        ViewHolder views = null;
        try
        {
            views = new ViewHolder(view);
        }
        catch(Exception e)
        { }
        return views;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Initialisation des listes
        listeRecyclable.addAll(Arrays.asList(tabRecyclable));
        listeNonRecyclable.addAll(Arrays.asList(tabNonRecyclabe));
        listeVerre.addAll(Arrays.asList(tabVerre));
        String text1="";
        String text2="";
        String text3="";
        int img1=0;
        int img2=0;
        int img3=0;
        try {
            String nomPdt = lpdt.get(position).getNom();
            String marquePdt = lpdt.get(position).getMarque();
            lpdt.get(position).loadImage();
            Drawable imagePdt = lpdt.get(position).getImage();
            //Affiche les poubelles correspondantes, tant que il n'y a pas de text
            //Ce qui veut dire qu'aucun emballage a était trouvé..
            int i=0;
            while(i<lpdt.get(position).emball.length && text1.isEmpty())
            {
                text1 = verificationNomDeEmballage(position,i,text1);
                img1=envoieImage(text1);
                i++;
            }
            while(i<lpdt.get(position).emball.length && text2.isEmpty())
            {
                text2 = verificationNomDeEmballage(position,i,text2);
                img2=envoieImage(text2);
                i++;
            }
            while(i<lpdt.get(position).emball.length && text3.isEmpty())
            {
                text3 = verificationNomDeEmballage(position,i,text3);
                img3=envoieImage(text3);
                i++;
            }

            holder.setData(nomPdt,marquePdt, imagePdt, img1, img2, img3);
        }
        catch (Exception ignored){}



    }

    @Override
    public int getItemCount() {
        return lpdt.size();
    }

    public String verificationNomDeEmballage(int position, int i, String text)
    {
        String _emballage = upperCaseFirst(lpdt.get(position).emball[i].replaceAll(" fr:","").replaceAll(" 100% recyclable",""));
        if (listeRecyclable.contains(_emballage))
        {
            text=_emballage;

        }
        else if (listeNonRecyclable.contains(_emballage))
        {
            text=_emballage;

        }
        else if(listeVerre.contains(_emballage))
        {
            text=_emballage;
        }
        return text;
    }

    public int envoieImage(String text)
    {
        int rss = 0;
        if (listeRecyclable.contains(text))
        {
            rss = R.drawable.poubelle_jaune;

        }
        else if (listeNonRecyclable.contains(text))
        {
            rss = R.drawable.poubelle_noire;
        }
        else if(listeVerre.contains(text))
        {
            rss = R.drawable.poubelle_verte;
        }
        return rss;
    }

    public static String upperCaseFirst(String val) {
        char[] arr = val.toCharArray();
        arr[0] = Character.toUpperCase(arr[0]);
        return new String(arr);
    }


}
