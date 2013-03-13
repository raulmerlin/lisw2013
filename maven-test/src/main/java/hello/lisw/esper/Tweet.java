package hello.lisw.esper;

public class Tweet {
    private String text;
    private String author;
    private double count;

    public Tweet(String text, String author, double count){
        this.text = text;
        this.author = author;
        this.count = count;
    }

    public String getText(){
        return text;
    }

    public String getAuthor(){
        return author;
    }

    public double getCount(){
        return count;
    }

    @Override
    public String toString(){
        return author + " : " + text;
    }
}
