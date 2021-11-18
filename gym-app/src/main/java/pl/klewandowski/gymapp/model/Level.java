package pl.klewandowski.gymapp.model;

public enum Level{
    BEGINNER("podstawowy"),
    INTERMEDIATE("średnio-zaawansowany"),
    ADVANCED("zaawansowany");

    private final String level;

    Level(String level){
        this.level = level;
    }
    public String getLevel(){
        return level;
    }
}
