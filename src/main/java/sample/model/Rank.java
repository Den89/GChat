package sample.model;

public enum Rank {
    SOLDIER_RANK(1, "a", "Soldier"),
    SERGEANT_RANK(2, "b", "Sergeant"),
    LIEUTENANT_RANK(3, "c", "Lieutenant"),
    GENERAL_RANK(4, "G", "General");
    private int rank;
    private String key;
    private String name;

    Rank(int rank, String key, String name) {
        this.rank = rank;
        this.key = key;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getRank() {
        return rank;
    }

    public String getKey() {
        return key;
    }
}
