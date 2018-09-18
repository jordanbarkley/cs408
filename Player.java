public class Player implements Comparable<Player> {
    public int timePlayed;
    public boolean hasSword;

    public Player(int timePlayed, boolean hasSword) {
        this.timePlayed = timePlayed;
        this.hasSword = hasSword;
    }

    @Override 
    public int compareTo(Player other) {
        return Integer.compare(this.timePlayed, other.timePlayed);
    }
}