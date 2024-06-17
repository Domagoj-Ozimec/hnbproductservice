package com.example.hnbproductservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
public class ExchangeRateResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("broj_tecajnice")
    @Column(nullable = false)
    private String brojTecajnice;

    @JsonProperty("datum_primjene")
    @Column(nullable = false)
    private String datumPrimjene;

    @JsonProperty("drzava")
    @Column(nullable = false)
    private String drzava;

    @JsonProperty("drzava_iso")
    @Column(nullable = false)
    private String drzavaIso;

    @JsonProperty("sifra_valute")
    @Column(nullable = false)
    private String sifraValute;

    @JsonProperty("valuta")
    @Column(nullable = false)
    private String valuta;

    @JsonProperty("jedinica")
    @Column(nullable = false)
    private int jedinica;

    @JsonProperty("kupovni_tecaj")
    @Column(nullable = false)
    private String kupovniTecaj;

    @JsonProperty("srednji_tecaj")
    @Column(nullable = false)
    private String srednjiTecaj;

    @JsonProperty("prodajni_tecaj")
    @Column(nullable = false)
    private String prodajniTecaj;


    // Getters and Setters
    public String getBrojTecajnice() {
        return brojTecajnice;
    }

    public String getDatumPrimjene() {
        return datumPrimjene;
    }

    public String getDrzava() {
        return drzava;
    }

    public String getDrzavaIso() {
        return drzavaIso;
    }

    public String getSifraValute() {
        return sifraValute;
    }

    public String getValuta() {
        return valuta;
    }

    public int getJedinica() {
        return jedinica;
    }

    public String getKupovniTecaj() {
        return kupovniTecaj;
    }

    public String getSrednjiTecaj() {
        return srednjiTecaj;
    }

    public String getProdajniTecaj() {
        return prodajniTecaj;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public void setProdajniTecaj(String prodajniTecaj) {
        this.prodajniTecaj = prodajniTecaj;
    }

    public void setSrednjiTecaj(String srednjiTecaj) {
        this.srednjiTecaj = srednjiTecaj;
    }

    public void setKupovniTecaj(String kupovniTecaj) {
        this.kupovniTecaj = kupovniTecaj;
    }

    public void setJedinica(int jedinica) {
        this.jedinica = jedinica;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    public void setSifraValute(String sifraValute) {
        this.sifraValute = sifraValute;
    }

    public void setDrzavaIso(String drzavaIso) {
        this.drzavaIso = drzavaIso;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public void setDatumPrimjene(String datumPrimjene) {
        this.datumPrimjene = datumPrimjene;
    }

    public void setBrojTecajnice(String brojTecajnice) {
        this.brojTecajnice = brojTecajnice;
    }

}

