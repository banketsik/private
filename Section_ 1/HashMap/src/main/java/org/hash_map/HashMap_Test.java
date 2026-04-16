package org.hash_map;


public class HashMap_Test {
    static void main() {
        Map<String, Integer> testMap1 = new HashMap_Exm<>();

        for(int i = 0; i < 16; i++) {
            String key = "# " + i;
            testMap1.put(key, i);
        }
        System.out.println("testMap1 size: " + testMap1.size());
        for(int i = 0; i < 16; i++) {
            String key = "# " + i;
            Integer value = testMap1.get(key);
            System.out.println("Key: " + key + " Value: " + value);
        }

        Map<Integer, String> testMap2 = new HashMap_Exm<>();
        for(int i = 0; i < 16; i++) {
            int key = i;
            String randomString = Long.toString(
                    Double.doubleToLongBits(
                            Math.random()), 36);
            testMap2.put(key,  randomString);
        }
        System.out.println(testMap2.size());
        for(int i = 0; i < 16; i++) {
            String value = testMap2.get(i);
            System.out.println("Key: " + i +  " Value: " + value);
        }

    }
}
