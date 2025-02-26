package a1_2201040166;
import java.util.Arrays;
import java.util.Random;


public class CoffeeTinGame {
    private static final char GREEN = 'G';
    private static final char BLUE = 'B';
    private static final char REMOVED = '-';
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

    public static void main(String[] args) {
        char[][] tins = {
                {BLUE, BLUE, BLUE, GREEN, GREEN},
                {BLUE, BLUE, BLUE, GREEN, GREEN, GREEN},
                {GREEN},
                {BLUE},
                {BLUE, GREEN}
        };

        for (int i = 0; i < tins.length; i++) {
            char[] tin = tins[i];
            int greens = 0;
            for (char bean : tin) {
                if (bean == GREEN)
                    greens++;
            }
            final char last = (greens % 2 == 1) ? GREEN : BLUE;
            System.out.printf("%nTIN (%d Gs): %s %n", greens, Arrays.toString(tin));
            char lastBean = tinGame(tin);
            System.out.printf("tin after: %s %n", Arrays.toString(tin));
            if (lastBean == last) {
                System.out.printf("last bean: %c%n", lastBean);
            } else {
                System.out.printf("Oops, wrong last bean: %c (expected: %c)%n", lastBean, last);
            }
        }
    }


    public static char tinGame(char[] tin) {
        for (char bean : tin) {
            if (bean != BLUE && bean != GREEN && bean != REMOVED) {
                throw new IllegalArgumentException("Tin contains invalid bean: " + bean);
            }
        }
        if (tin.length == 0) {
            return NULL;
        }
        while (hasAtLeastTwoBeans(tin)) {
            char beanOne = takeOne(tin);
            char beanTwo = takeOne(tin);
            updateTin(tin, beanOne, beanTwo);
        }
        return anyBean(tin);
    }


    public static char takeOne(char[] tin) {
        int count = 0;
        for (char bean : tin) {
            if(bean != REMOVED){
                count++;
            }
        }
        if (count == 0){
            return NULL; //het
        }
        int index;
        do {
            index = randInt(tin.length);
        }while (tin[index] == REMOVED);
        char bean = tin[index];
        tin[index] = REMOVED;
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
            return NULL; //het
        }
        int index;
        do {
            index = randInt(beansBag.length);
        }while  (beansBag[index] != beanType);
        char bean = beansBag[index];
        beansBag[index] = REMOVED;
        return bean;
    }
    private static boolean hasAtLeastTwoBeans(char[] tin) {
        int count = 0;
        for (char bean : tin) {
            if (bean != REMOVED) {
                count++;
            }
            if (count >= 2)
                return true;
        }
        return false;
    }


    private static char[] takeTwo(char[] tin) {
        char first = takeOne(tin);
        char second = takeOne(tin);

        return new char[]{first, second};
    }

    public static void updateTin(char[] tin, char beanOne, char beanTwo){
        if(beanOne == NULL || beanTwo == NULL){
            return;
        }
        if (beanOne == beanTwo){
            putIn(tin, getBean(BeansBag, BLUE));
        }else putIn(tin, GREEN);
    }
    private static void putIn(char[] tin, char bean) {
        for (int i = 0; i < tin.length; i++) {
            if (tin[i] == REMOVED) {
                tin[i] = bean;
                break;
            }
        }
    }

    private static char anyBean(char[] tin) {
        for (char bean : tin) {
            if (bean != REMOVED) {
                return bean;
            }
        }
        return NULL;
    }
}
