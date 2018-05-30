package univ.m2acdi.apprentissageborel.util;

import java.io.Serializable;

public class BMObject implements Serializable {

    private String son;
    private String graphie;
    private String texte_ref;
    private String geste;
    private String motRef;
    private String imgMot;

    public BMObject(String son, String graphie, String texte_ref, String geste) {
        this.son = son;
        this.graphie = graphie;
        this.texte_ref = texte_ref;
        this.geste = geste;
    }

    public BMObject(String son, String graphie, String texte_ref, String geste, String motRef,String imgMot) {
        this.son = son;
        this.graphie = graphie;
        this.texte_ref = texte_ref;
        this.geste = geste;
        this.motRef = motRef;
        this.imgMot = imgMot;
    }

    public BMObject() {
    }

    public String getSon() {
        return son;
    }

    public void setSon(String son) {
        this.son = son;
    }

    public String getGraphie() {
        return graphie;
    }

    public void setGraphie(String graphie) {
        this.graphie = graphie;
    }

    public String getTexte_ref() {
        return texte_ref;
    }

    public void setTexte_ref(String texte_ref) {
        this.texte_ref = texte_ref;
    }

    public String getGeste() {
        return geste;
    }

    public void setGeste(String geste) {
        this.geste = geste;
    }

    public String getMotRef() {return motRef;}

    public void setMotRef(String motRef) {
        this.motRef = motRef;
    }

    public String getImgMot() {
        return imgMot;
    }

    public void setImgMot(String imgMot) {
        this.imgMot = imgMot;
    }
}
