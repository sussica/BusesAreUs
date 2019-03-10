package ca.ubc.cs.cpsc210.translink.tests;

import java.util.Random;

public abstract class AutoTestConf {
    public static Random RND = new Random();
    public static final int MAX_TEST = 131072;
    public static final double DELTA = 1.0e-8;  // tolerance for testing equality on doubles
    public static final int MAX_STR_LEN =1024;

    public static class RandomStringGen{
        private static final String defaultSet =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz-+=_,.<>!@#$%^&*()";
        private RandomStringGen(){}

        public static String generate(int l){
            return RandomStringGen.generate(l,RandomStringGen.defaultSet);
        }

        public static String generate(int l, String cS){
            int len = cS.length();
            return 0==l?new String():cS.charAt(AutoTestConf.RND.nextInt(len))
                    + generate(l-1,cS);
        }
    }
}
