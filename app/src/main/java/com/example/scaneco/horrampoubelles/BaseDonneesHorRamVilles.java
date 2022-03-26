package com.example.scaneco.horrampoubelles;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.concurrent.Callable;

class BaseDonneesHorRamVilles implements Callable<String> {
    private final String input;
    private final String userAgent;

    public BaseDonneesHorRamVilles(String input, String userAgent) {
        this.input = input;
        this.userAgent = userAgent;
    }

    @Override
    public String call() throws IOException {
        URLConnection urlConnection = new URL(input).openConnection();
        urlConnection.setRequestProperty("User-Agent", userAgent);
        return new Scanner(urlConnection.getInputStream()).useDelimiter("\\A").next();
    }
}
