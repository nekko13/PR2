package a1_2201040166;
import java.util.Arrays;
import java.util.Random;

/**
 * @overview A program that performs the coffee tin game on a
 *    tin of beans and display result on the standard output.
 *
 */
public class CoffeeTinGame {
    /** constant value for the green bean*/
    private static final char GREEN = 'G';
    /** constant value for the blue bean*/
    private static final char BLUE = 'B';
    /** constant for removed beans */
    private static final char REMOVED = '-';
    /** the null character*/
    private static final char NULL = '\u0000';
    private static final char[] BeansBag = {
            'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B',
            'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G', 'G',
            '-', '-', '-', '-', '-', '-', '-', '-', '-', '-'
    };
    private static final Random random = new Random();
    public static int randInt(int n){
        if(n <= 0){
            throw new IllegalArgumentException("n must be positive");
        }
        return random.nextInt(n);
    }
    /**
     * the main procedure
     * @effects
     *    initialise a coffee tin
     *    {@link TextIO#putf(String, Object...)}: print the tin content
     *    {@link @tinGame(char[])}: perform the coffee tin game on tin
     *    {@link TextIO#putf(String, Object...)}: print the tin content again
     *    if last bean is correct
     *      {@link TextIO#putf(String, Object...)}: print its colour
     *    else
     *      {@link TextIO#putf(String, Object...)}: print an error message
     */
    public static void main(String[] args) {
        // initialise some beans
        char[][] tins = {
                {BLUE, BLUE, BLUE, GREEN, GREEN},
                {BLUE, BLUE, BLUE, GREEN, GREEN, GREEN},
                {GREEN},
                {BLUE},
                {BLUE, GREEN}
        };

        for (int i = 0; i < tins.length; i++) {
            char[] tin = tins[i];

            // expected last bean
            // p0 = green parity /\
            // (p0=1 -> last=GREEN) /\ (p0=0 -> last=BLUE)
            // count number of greens
            int greens = 0;
            for (char bean : tin) {
                if (bean == GREEN)
                    greens++;
            }
            // expected last bean
            final char last = (greens % 2 == 1) ? GREEN : BLUE;

            // print the content of tin before the game
            System.out.printf("%nTIN (%d Gs): %s %n", greens, Arrays.toString(tin));

            // perform the game
            // get actual last bean
            char lastBean = tinGame(tin);
            // lastBean = last \/ lastBean != last

            // print the content of tin and last bean
            System.out.printf("tin after: %s %n", Arrays.toString(tin));

            // check if last bean as expected and print
            if (lastBean == last) {
                System.out.printf("last bean: %c%n", lastBean);
            } else {
                System.out.printf("Oops, wrong last bean: %c (expected: %c)%n", lastBean, last);
            }
        }
    }

    /**
     * Performs the coffee tin game to determine the colour of the last bean
     *
     * @requires tin is not null /\ tin.length > 0
     * @modifies tin
     * @effects <pre>
     *   take out two beans from tin
     *   if same colour
     *     throw both away, put one blue bean back
     *   else
     *     put green bean back
     *   let p0 = initial number of green beans
     *   if p0 = 1
     *     result = `G'
     *   else
     *     result = `B'
     *   </pre>
     */
    public static char tinGame(char[] tin) {
        for (char bean : tin) {
            if (bean != 'B' && bean != 'G' && bean != '-') {
                throw new IllegalArgumentException("Tin contains invalid bean: " + bean);
            }
        }
        if (tin.length == 0) {
            return '\u0000';
        }
        while (hasAtLeastTwoBeans(tin)) {
            char beanOne = takeOne(tin);
            char beanTwo = takeOne(tin);
            updateTin(tin, beanOne, beanTwo);
        }
        return anyBean(tin);
    }

    /**
     * @effects
     *  if tin has at least two beans
     *    return true
     *  else
     *    return false
     */
    public static char takeOne(char[] tin) {
        int count = 0;
        for (char bean : tin) {
            if(bean != '-'){
                count++;
            }
        }
        if (count == 0){
            return '\u0000'; //het
        }
        int index;
        do {
            index = randInt(tin.length);
        }while (tin[index] == '-');
        char bean = tin[index];
        tin[index] = '-';
        return bean;
    }
    public static char getBean(char[] beansBag, char beanType){
        int count = 0;
        for (char bean : beansBag) {
            if(bean == beanType){
                count++;
            }
        }
        if (count == 0){
            return '\u0000'; //het
        }
        int index;
        do {
            index = randInt(beansBag.length);
        }while  (beansBag[index] != beanType);
        char bean = beansBag[index];
        beansBag[index] = '-';
        return bean;
    }
    private static boolean hasAtLeastTwoBeans(char[] tin) {
        int count = 0;
        for (char bean : tin) {
            if (bean != '-') {
                count++;
            }

            if (count >= 2) // enough beans
                return true;
        }

        // not enough beans
        return false;
    }

    /**
     * @requires tin has at least 2 beans left
     * @modifies tin
     * @effects
     *  remove any two beans from tin and return them
     */
    private static char[] takeTwo(char[] tin) {
        char first = takeOne(tin);
        char second = takeOne(tin);

        return new char[]{first, second};
    }

    /**
     * @requires tin has at least one bean
     * @modifies tin
     * @effects
     *   remove any bean from tin and return it
     */


    /**
     * @requires tin has vacant positions for new beans
     * @modifies tin
     * @effects
     *   place bean into any vacant position in tin
     */
    public static void updateTin(char[] tin, char beanOne, char beanTwo){
        if(beanOne == '\u0000' || beanTwo == '\u0000'){
            return;
        }
        if (beanOne == beanTwo){
            putIn(tin, 'B');
        }else{
            putIn(tin, 'G');
        }
    }
    private static void putIn(char[] tin, char bean) {
        for (int i = 0; i < tin.length; i++) {
            if (tin[i] == '-') { // vacant position
                tin[i] = bean;
                break;
            }
        }
    }

    /**
     * @effects
     *  if there are beans in tin
     *    return any such bean
     *  else
     *    return '\u0000' (null character)
     */
    private static char anyBean(char[] tin) {
        for (char bean : tin) {
            if (bean != '-') {
                return bean;
            }
        }

        // no beans left
        return NULL;
    }
}
