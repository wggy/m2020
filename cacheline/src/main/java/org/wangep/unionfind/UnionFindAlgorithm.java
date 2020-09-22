package org.wangep.unionfind;

/***
 * created by wange on 2020/8/31 10:19
 */
public class UnionFindAlgorithm {

    private static int N = 10;
    private static int[] f = new int[N];

    static {
        init();
    }

    private static void init() {
        for (int i = 0; i < N; i++) {
            f[i] = i;
        }
    }

    private static void merge(int a, int b) {
        f[getFather(a)] = getFather(b);
    }

    private static int getFather(int x) {
        return f[x] == x ? x : getFather(f[x]);
    }

    private static Boolean query(int a, int b) {
        return getFather(a) == getFather(b);
    }

    public static void main(String[] args) {
        merge(3, 1);
        merge(1, 4);
        System.out.println(getFather(3));
        System.out.println(query(3, 1));
    }

}
