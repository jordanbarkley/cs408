import java.io.*; 
import java.util.*;
import java.lang.Math;

// See Homework1.pdf and input0.txt for more information on implementation.

public class Homework1 {

    public int A;
    public int B;
    public int N;
    public Player[] friends;

    public static void main(String[] args) {
        Homework1 h = new Homework1();
        if (args.length == 1) {
            h.runTest(args[0], 0, true);
        } else {
            h.runTest("input0.txt", 4, false);
            h.runTest("input1.txt", 6, false);
            h.runTest("input2.txt", 0, false);
            h.runTest("input3.txt", 0, false);
            h.runTest("input4.txt", 6, false);
            h.runTest("input5.txt", 13, false);
            h.runTest("input6.txt", 12, false);
        }
    }

    public void runTest(String filename, int expected, boolean isCommandLineArgument) {
        // create scanner based on filename
        Scanner s = null;
        try {
            s = new Scanner(new File(filename));
        } catch (FileNotFoundException fnfe) {
            // if the file does not exist
            System.out.println("File not found! Test Fails!");
            return;
        } catch (Exception e) {
            // if something goes very wrong
            System.out.println(e.toString());
            return;        
        }

        // get input from file
        this.N = s.nextInt();
        this.A = s.nextInt();
        this.B = s.nextInt();

        // init friends array
        this.friends = new Player[this.N];
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
            Player friend = new Player(timePlayed, hasSword);
            this.friends[i] = friend;
        }

        // sort friends by timePlayed
        Arrays.sort(friends);

        // attempt to scrap various values from iteration
        // 10 30 50
        // NS 19 (scrapped, too low)
        // NS 22 (scrapped, too low)
        // NS 28
        // NS 33
        // S  35
        // S  40
        // S  45
        // NS 51
        // NS 52 (scrapped, too high)
        // S  59 (scrapped, too high)

        // scrapping requires that there more than 2 elements in the array
        if (this.friends.length > 2) {
            // scrap low
            for (int i = 0; i < this.friends.length - 1; i++) {
                if (this.friends[i + 1].timePlayed < this.A) {
                    this.friends[i].relevant = false;
                }
            }

            // scrap high
            for (int i = this.friends.length - 1; i > 0; i--) {
                if (this.friends[i - 1].timePlayed > this.B) {
                    this.friends[i].relevant = false;
                }
            }
        }

        // init "return" value
        int swordedOpponents = 0;

        // variables for algorithm
        Player friendA = new Player(1, false);
        Player friendB = new Player(1, false);
        int length = 0;

        // declare index for friends array
        int i = 0;
        boolean countFirst = true;

        // increment through irrelevant low values
        while (this.friends[i].relevant == false && i < this.friends.length) {
            i++;
        }

        // show time
        while (i < this.friends.length - 1) {
            // init friendA and friendB
            friendA = this.friends[i];
            friendB = this.friends[i + 1];

            // break after friendB becomes irrelevant
            if (friendB.relevant == false) {
                break;
            }
            
            // calculate length represented between friendA and friendB
            length = friendB.timePlayed - friendA.timePlayed + 1;

            if (friendA.hasSword && friendB.hasSword) {
                // case: both friends have sword

                // c comes from the following instruction.
                // "If P' has the item, he predicts that the new player P also has the item."
                // Since both players have the sword, all entries should be added. However, on
                // any run after a "break", we subtract 1 to avoid double counting. A break can
                // be described as anytime as the gap caused by friendB not having a sword. See
                // input6.txt for an example.

                swordedOpponents += length;
                if (countFirst) {
                    countFirst = false;
                } else {
                    swordedOpponents -= 1;
                }

            } else if (friendA.hasSword) {
                // case: only friendA has sword

                // (c / 2) comes from the following instruction.
                // "If P' has the item, he predicts that the new player P also has the item."
                // Since we know only one player has the sword (the both check above fails),
                // we can conclude that only half the players in the list should have one.

                // (c % 2) comes from the following instruction.
                // "If there are more than one closest player among his friends, he guessed the new 
                // player P has the item if any one of them have it.""
                // Essentially, if there's an odd number, we need to be sure to include the extra.

                // Lastly, this is the only case where countFirst is reset back to true
                
                // add sworded opponents (half or half + 1)
                swordedOpponents += (length / 2) + (length % 2);
                
                // check if we should count the first point
                if (!countFirst) {
                    swordedOpponents -= 1;
                }
            
                // update countFirst
                countFirst = true;

            } else if (friendB.hasSword) {
                // case: only friendB has sword

                // This case is essentially the same as the above. However, it does not require
                // countFirst to be set to true.

                // add sworded opponents (half or half + 1)
                swordedOpponents += (length / 2) + (length % 2);

                // check if we should count the first point and if so, set countFirst to false
                if (countFirst) {
                    countFirst = false;
                } else {
                    swordedOpponents -= 1;
                }
            }

            // update swordedOpponents for opponents not in range (if necessary).
            // low
            if (friendA.timePlayed < this.A && friendA.hasSword) {
                swordedOpponents -= (this.A - friendA.timePlayed);
            }

            // high
            if (friendB.timePlayed > this.B && friendB.hasSword) {
                swordedOpponents -= (friendB.timePlayed - this.B);
            }

            // increment i and attempt loop
            i++;
        }

        // In some cases, the min friend.timePlayed will be larger than A. When this is the case
        // and min friend.hasSword == true, all opponents from A to friendA.timePlayed should be
        // included.
        Player friendMin = this.friends[0];
        if (friendMin.timePlayed > this.A && friendMin.timePlayed <= this.B && friendMin.hasSword) {
            swordedOpponents += (friendMin.timePlayed - this.A);
        }

        // Like the case above, sometimes the max friend.timePlayed will be larger than B. We should
        // adjust for this as well.
        if (friendB.relevant && this.B > friendB.timePlayed) {
            swordedOpponents += (this.B - friendB.timePlayed);
        }

        // close scanner
        s.close();

        // finish
        if (isCommandLineArgument) {
            // write opponentsWithSword to file
            try {
                PrintWriter p = new PrintWriter("output.txt");
                p.println(swordedOpponents);
                p.close();
            } catch (FileNotFoundException e) {
                System.out.println("File write faile!");
            } 
        } else if (expected == swordedOpponents) {
            // print passed to stdout
            System.out.printf("Test '%s' passed!\n", filename);
        } else {
            // print failed to stdout
            System.out.printf("Test '%s' failed! (calculated value %d != expected value %d)\n", 
                filename, swordedOpponents, expected);
        }
    }
}

