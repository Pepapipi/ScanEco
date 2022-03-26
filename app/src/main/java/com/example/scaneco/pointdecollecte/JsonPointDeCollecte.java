package com.example.scaneco.pointdecollecte;

import static java.lang.Float.parseFloat;

import android.util.JsonReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;



public class JsonPointDeCollecte {

    public static List<PointDeCollecte> valeurRenvoyeeJson(String fichierJson) throws Exception{

        List<PointDeCollecte> listeRenvoyee= new ArrayList<>();
        try (JsonReader reader = new JsonReader(new StringReader(fichierJson)))
        {
            reader.beginObject();
            while (reader.hasNext()) {
                reader.nextName();
                reader.beginArray();
                while (reader.hasNext()) {
                    reader.beginObject();
                    reader.nextName();
                    reader.beginArray();

                    while (reader.hasNext()) {
                        reader.beginObject();
                        PointDeCollecte m = new PointDeCollecte();
                        while (reader.hasNext()) {
                            String varibale = reader.nextName();
                            switch (varibale) {
                                case "type":
                                    m.setType(reader.nextString());
                                    break;
                                case "longitude":
                                    m.setLongitude(parseFloat(reader.nextString()));
                                    break;
                                case "latitude":
                                    m.setLatitude(parseFloat(reader.nextString()));
                                    break;
                                case "adresse":
                                    m.setAdresse(reader.nextString());
                                    break;
                                case "ville":
                                    m.setVille(reader.nextString());
                                    break;
                            }

                        }
                        listeRenvoyee.add(m);
                        reader.endObject();
                    }
                    reader.endArray();
                    reader.endObject();
                }
                reader.endArray();
            }
            reader.endObject();
        }
        catch (Exception e)
        {
            throw e;
        }
        return listeRenvoyee;
    }
}
