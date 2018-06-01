package univ.m2acdi.apprentissageborel.util;

public class ExerciseObject {

    private String mot;
    private String allographe;
    private String son;

    public ExerciseObject(){

    }

    public ExerciseObject(String mot, String allographe, String son) {
        this.mot = mot;
        this.allographe = allographe;
        this.son = son;
    }

    public String getMot() {
        return mot;
    }

    public void setMot(String mot) {
        this.mot = mot;
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
