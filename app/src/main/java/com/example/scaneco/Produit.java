package com.example.scaneco;

import android.graphics.drawable.Drawable;
import android.util.JsonReader;
import android.widget.ImageView;


import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Récupère à partir de données json où un code barres avec l'API d'OpenFoodFacts un objet Produit
 * avec son code barres, son nom, ses marques ainsi que ses instructions pour le tri de ses
 * emballages.
 *
 * Pour créer un objet produit il faut soit utiliser le constructeur avec des données json ayant au
 * minimum comme forme :
 * <pre>
 *     {@code
 *     {
 *         "code": "123456789",
 *         "product":{
 *             "brands": "Une Marque, Une autre Marque, ...",
 *             "product_name": "Nom du produit",
 *             "packaging_text_fr": "Instruction de recyclage"
 *         },
 *         "status": 1,
 *         "status_verbose": "product found"
 *     }}
 * </pre>
 *
 * Ou invoquer la methode statique {@link #getProductFromBarCode} avec le code barres du produit
 * recherché. <strong>Cette methode ne marche seulement si les permissions internet ont été
 * accordées</strong>. Pour cela il suffit d'ajouter au fichier <strong>AndroidManifest.xml</strong>
 * la ligne :
 * {@code <uses-permission android:name="android.permission.INTERNET"/>}.
 */
public class Produit implements Serializable {
    private String code;
    private String nom;
    private String marques;
    private String texteEmbalage;
    private String urlImage;
    private transient Drawable image;
    private String[] emball;

    /**
     * Construit un produit à partir de données json.
     * @param json Données json sous forme de chaîne de caractères.
     */
    public Produit(String json) {
        try (JsonReader reader = new JsonReader(new StringReader(json))) {
            reader.beginObject();
            while (reader.hasNext()){
                String name = reader.nextName();
                switch (name) {
                    case "code":
                        this.code = reader.nextString();
                        break;
                    case "product":
                        reader.beginObject();
                        while (reader.hasNext()){
                            name = reader.nextName();
                            switch (name) {
                                case "brands":
                                    this.marques = reader.nextString();
                                    break;
                                case "product_name":
                                    this.nom = reader.nextString();
                                    break;
                                case "packaging_text_fr":
                                    this.texteEmbalage = reader.nextString();
                                    break;
                                case "image_url":
                                    this.urlImage = reader.nextString();
                                    break;
                                case "packaging":
                                    setEmball(reader.nextString().split(","));
                                    break;

                                default:
                                    reader.skipValue();
                                    break;
                            }
                        }
                        reader.endObject();
                        break;
                    case "status":
                        int status = reader.nextInt();
                        reader.nextName();
                        if (status == 0) {
                            setNom("Désolé le produit recherché n'a pas été trouvé dans la base de données d'OpenFoodFacts.");
                        } else {
                            reader.nextString();
                        }
                        break;
                    default:
                        reader.skipValue();
                        break;
                }

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public Produit(){}

    /**
     *
     * @return Le code barres du produit.
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @return Le nom du produit.
     */
    public String getNom() {
        return nom;
    }

    /**
     *
     * @return La liste des marques du produit.
     */
    public String getMarque() {
        return marques;
    }

    /**
     *
     * @return Les instructions vis-à-vis des embalages.
     */
    public String getTexteEmballage() {
        return texteEmbalage;
    }


    /**
     *
     * @return L'image du produit.
     */
    public String getUrlImage() {
        return urlImage;
    }

    public Drawable getImage(){return  image;}

    public void loadImage() {
        TaskRunner taskRunner = new TaskRunner();
        taskRunner.executeAsync(new ImageProduit(urlImage, MainActivity.USER_AGENT), data -> image = data);
    }

    public void loadImageInView(ImageView imageView){
        TaskRunner taskRunner = new TaskRunner();
        taskRunner.executeAsync(new ImageProduit(urlImage, MainActivity.USER_AGENT), imageView::setImageDrawable);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setMarques(String marques) {
        this.marques = marques;
    }

    public void setTexteEmbalage(String texteEmbalage) {
        this.texteEmbalage = texteEmbalage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }


    public static void getProductFromBarCode(String barCode, Cmd cmd) {
        TaskRunner taskRunner = new TaskRunner();
        String url = "https://fr.openfoodfacts.org/api/v0/product/" + barCode + ".json";
        taskRunner.executeAsync(new OpenFoodFactsAPI(url, MainActivity.USER_AGENT), data->cmd.execute(new Produit(data)));
    }

    public interface Cmd{
        void execute(Produit produit);
    }



    /**
     * Créé et renvoit une liste de Produits à partir d'un String contenant des données Json.
     * @param json String contenant des données Json.
     * @return List de Produits.
     * @throws IOException Si les données json fournies ne sont pas valides.
     */
    @NonNull
    public static List<Produit> getProductsListFromJson(String json) throws IOException{

        List<Produit> produits = new ArrayList<>();
        try(JsonReader reader = new JsonReader(new StringReader(json))){
            reader.beginObject();
            String name;
            while (reader.hasNext()){
                name = reader.nextName();
                if (name.equals("products")){
                    reader.beginArray();
                    while (reader.hasNext()){
                        reader.beginObject();
                        Produit produit = new Produit();
                        while (reader.hasNext()){
                            name = reader.nextName();
                            switch (name){
                                case "code":
                                    produit.setCode(reader.nextString());
                                    break;
                                case "product_name":
                                    produit.setNom(reader.nextString());
                                    break;
                                case "brands":
                                    produit.setMarques(reader.nextString());
                                    break;
                                case "packaging_text_fr":
                                    produit.setTexteEmbalage(reader.nextString());
                                    break;
                                case "image_url":
                                    produit.setUrlImage(reader.nextString());
                                    break;
                                case "packaging":
                                    produit.setEmball(reader.nextString().split(","));
                                    break;
                                default:
                                    reader.skipValue();
                                    break;
                            }
                        }
                        produits.add(produit);
                        reader.endObject();
                    }
                    reader.endArray();
                }
                else {
                    reader.skipValue();
                }
            }
            reader.endObject();
        }

        return produits;
    }

    public String[] getEmball() {
        return emball;
    }

    public void setEmball(String[] emball) {
        this.emball = emball;
    }
}
