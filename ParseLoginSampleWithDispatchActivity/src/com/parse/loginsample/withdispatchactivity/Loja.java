package com.parse.loginsample.withdispatchactivity;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by Patrick on 11/12/2014.
 */
public class Loja {

    private String id;
    private String nome;

    //private String ;
    Loja(String lojaId, String lojaNome) {
        id = lojaId;
        nome = lojaNome;
    }


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return this.getNome();
    }
}
