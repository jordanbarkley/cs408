public class Friend implements Comparable<Friend> {
    public int timePlayed;
    public boolean hasSword;

    public Friend(int timePlayed, boolean hasSword) {
        this.timePlayed = timePlayed;
        this.hasSword = hasSword;
    }

    @Override 
    public int compareTo(Friend other) {
        return Integer.compare(this.timePlayed, other.timePlayed);
    }
}