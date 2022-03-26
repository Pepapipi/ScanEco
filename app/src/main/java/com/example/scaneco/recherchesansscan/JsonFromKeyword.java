package com.example.scaneco.recherchesansscan;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.concurrent.Callable;

class JsonFromKeyword implements Callable<String> {
    private final String input;
    private final String page;
    private final String userAgent;

    public JsonFromKeyword(String input, String page, String userAgent) {
        this.input = input.replace(" ", "+");
        this.page = page;
        this.userAgent = userAgent;
    }

    @Override
    public String call() throws IOException {
        URLConnection urlConnection = new URL("https://fr.openfoodfacts.org/cgi/search.pl?action=process&search_terms=" + input + "&sort_by=unique_scans_n&page_size=24&page="+page+"&json=1").openConnection();
        urlConnection.setRequestProperty("User-Agent", userAgent);
        return new Scanner(urlConnection.getInputStream()).useDelimiter("\\A").next();
    }
}
