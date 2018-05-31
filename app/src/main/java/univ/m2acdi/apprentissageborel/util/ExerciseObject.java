package univ.m2acdi.apprentissageborel.util;

public class ExerciseObject {

    private String mots;
    private String allographe;
    private String son;

    public ExerciseObject(){

    }

    public ExerciseObject(String mots, String allographe, String son) {
        this.mots = mots;
        this.allographe = allographe;
        this.son = son;
    }

    public String getMots() {
        return mots;
    }

    public void setMots(String mots) {
        this.mots = mots;
    }

    public String getAllographe() {
        return allographe;
    }

    public void setAllographe(String allographe) {
        this.allographe = allographe;
    }

    public String getSon() {
        return son;
    }

    public void setSon(String son) {
        this.son = son;
    }
}
