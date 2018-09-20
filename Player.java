public class Player implements Comparable<Player> {
    public int timePlayed;
    public boolean hasSword;
    public boolean relevant;

    public Player(int timePlayed, boolean hasSword) {
        this.timePlayed = timePlayed;
        this.hasSword = hasSword;
        this.relevant = true;
    }

    @Override 
    public int compareTo(Player other) {
        return Integer.compare(this.timePlayed, other.timePlayed);
    }
}