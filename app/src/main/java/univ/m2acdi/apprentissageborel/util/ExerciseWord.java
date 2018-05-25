package univ.m2acdi.apprentissageborel.util;

public class ExerciseWord {

    private String word;
    private String []graphie;
    private String [] son;
    private int length;
    private int index;

    public ExerciseWord(String graphie, String son){
        this.graphie=graphie.split(" ");
        this.son=son.split(" ");
        this.word="";
        for(String x: this.graphie)
            this.word+=x;
        if(this.graphie.length == this.son.length)
            this.length=this.graphie.length;
        this.index=0;
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String[] getGraphie() {
        return graphie;
    }

    public void setGraphie(String[] graphie) {
        this.graphie = graphie;
    }

    public String[] getSon() {
        return son;
    }

    public void setSon(String[] son) {
        this.son = son;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
