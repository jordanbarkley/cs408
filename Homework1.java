import java.io.*; 
import java.util.*; 
import java.lang.Math;

public class Homework1 {

    public int A;
    public int B;
    public int N;
    public int T;
    public Friend[] friends;
    public int[] times;

    public static void main(String[] args) {
        System.out.printf("Hello World!\n");
        Homework1 h = new Homework1();
        h.runTest("input0.txt", 4);
    }

    public boolean runTest(String filename, int expected) {
        // create scanner based on filename
        Scanner s = null;
        try {
            s = new Scanner(new File(filename));
        } catch (FileNotFoundException fnfe) {
            System.out.printf("File not found! Test Fails!\n");
            return false;
        } catch (Exception e) {
            System.out.printf("Something went very wrong here!\n");
            return false;
        }

        // get input from file
        this.N = s.nextInt();
        this.A = s.nextInt();
        this.B = s.nextInt();

        // init friends array
        this.friends = new Friend[this.N];
        boolean hasSword;
        int timePlayed;

        // populate friends array
        for (int i = 0; i < this.friends.length; i++) {
            if (s.next().equals("S")) {
                hasSword = true;
            } else {
                hasSword = false;
            }
            timePlayed = s.nextInt();
            Friend friend = new Friend(timePlayed, hasSword);
            this.friends[i] = friend;
        }

        // sort friends by timePlayed
        Arrays.sort(friends);

        // initialize "return" value
        int opponentsWithSword = 0;

        // friend objects for finding P'
        Friend friendLow;
        Friend friendHigh;

        // P' index
        int j = 0;

        // iterate through friends to determine opponents in O(n)
        for (int i = this.A; i <= this.B; i++) {
            // find which value i is closer to (find P')
            friendLow = friends[j];
            friendHigh = friends[j + 1];
            int c = closer(friendLow.timePlayed, friendHigh.timePlayed, i);

            // check P'
            if (c == -1 && friendLow.hasSword) {
                // "If P' has the item, he predicts that the new player P also has the item."
                opponentsWithSword++;
            } else if (c == 1 && friendHigh.hasSword) {
                // "If P' has the item, he predicts that the new player P also has the item."
                opponentsWithSword++;
            } else if (c == 0 && (friendLow.hasSword || friendHigh.hasSword)) {
                // "If there are more than one closest player among his friends, he guessed the new 
                // player P has the item if any one of them have it.""
                opponentsWithSword++;
            } else {
                // "If P' does not have the item, he guessed that the new player P does not have
                // the item"
                // do nothing
            }

            // increment j
            if (i == friendHigh.timePlayed) {
                j++;
            }
        }

        // close scanner
        s.close();

        // print result
        if (expected == opponentsWithSword) {
            System.out.printf("Test '%s' passed!\n", filename);
            return true;
        } else {
            System.out.printf("Test '%s' failed! (expected value %d does not equal %d)\n", 
            filename, expected, opponentsWithSword);
            return false;
        }
    }

    public int closer(int low, int high, int val) {
        int lowTest = Math.abs(val - low);
        int highTest = Math.abs(high - val);

        if (highTest < lowTest) {
            return 1;
        } else if (lowTest < highTest) {
            return -1;
        } else {
            return 0;
        }
    }
}

