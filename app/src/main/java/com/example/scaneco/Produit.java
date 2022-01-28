package com.example.scaneco;

import android.content.res.Resources;
import android.util.JsonReader;
import android.util.JsonToken;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/**
 * Récupère à partir de données json où un code barres avec l'API d'OpenFoodFacts un objet Produit
 * avec son code barres, son nom, ses marques ainsi que ses instructions pour le tri de ses
 * embalages.
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
public class Produit {
    private String code;
    private String nom;
    private List<String> marques;
    private String texteEmbalage;
    private String image;


    /**
     * Construit un produit à partir de données json.
     * @param json Données json sous forme de chaîne de caractères.
     * @throws IOException Si les données json fournies ne sont pas valides.
     * @throws Resources.NotFoundException Si le produit n'a pas été trouvé.
     */
    public Produit(String json) throws IOException, Resources.NotFoundException {
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
                                    String brands = reader.nextString();
                                    this.marques = Arrays.asList(brands.split("\\s*,\\s*"));
                                    break;
                                case "product_name":
                                    this.nom = reader.nextString();
                                    break;
                                case "packaging_text_fr":
                                    this.texteEmbalage = reader.nextString();
                                    break;
                                case "image_url":
                                    this.image = reader.nextString();
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
                        if (status == 0) {
                            name = reader.nextName();
                            throw new Resources.NotFoundException(reader.nextString());
                        } else {
                            reader.nextName();
                            reader.nextString();
                        }
                        break;
                }

            }
        }
    }

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
    public List<String> getMarques() {
        return marques;
    }

    /**
     *
     * @return Les instructions vis-à-vis des embalages.
     */
    public String getTexteEmbalage() {
        return texteEmbalage;
    }


    /**
     *
     * @return L'image du produit.
     */
    public String getImage() {
        return image;
    }

    /**
     * Créé et renvoit un Produit à partir d'un code barres. Seulement si les permissions internet
     * ont été accordées.
     *
     * @param barCode Code barres du produit sous forme de chaîne de caractères.
     * @return Le produit correspondant au code barres.
     * @throws IOException Si les données json fournies ne sont pas valides.
     * @throws Resources.NotFoundException Si le produit n'a pas été trouvé.
     * @throws CancellationException If the computation was cancelled.
     * @throws ExecutionException If the computation threw an exception.
     * @throws InterruptedException If the current thread was interrupted while waiting.
     */
    public static Produit getProductFromBarCode(String barCode) throws IOException, Resources.NotFoundException, CancellationException, ExecutionException, InterruptedException {
        return new Produit(new OpenFoodFactsAPI().execute("https://fr.openfoodfacts.org/api/v0/product/" + barCode + ".json").get());
    }
}
