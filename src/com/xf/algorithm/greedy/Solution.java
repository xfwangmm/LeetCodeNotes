package com.xf.algorithm.greedy;

import java.util.*;

/**
 * @author xfwaaang
 * @create 2019-03-26 10:19
 * 贪心
 */
public class Solution {


    /**
     * 392. Is Subsequence
     * pass     78%     93%
     * 遍历s，若s中的某个字符不能在t的剩余字符中找到，则返回false
     * @param s
     * @param t
     * @return
     */
    public boolean isSubsequence(String s, String t) {
        int k = 0;
        for (int i=0; i<s.length(); i++){
            while (k < t.length() && t.charAt(k) != s.charAt(i))    k++;
            if (k == t.length())    return false;
            k++;
        }
        return true;
    }

    /**
     * 406. Queue Reconstruction by Height
     * pass   17%  80%
     * 按 h 和 k，从小到大排序people
     * 遍历people，每次确定一个元素的位置，此元素为当前最小的元素（长度为2的一维数组）
     * 对于当前最小的元素 [h,k] ，它最终所在位置必须满足的条件为：
     * 当前情况下，它前面有k个身高大于等于h的元素，即 k = 前方空位个数 + 前方身高等于h元素的个数
     * 找到距离满足条件位置最近的空位插入
     * @param people
     * @return
     */
    public static int[][] reconstructQueue(int[][] people) {
        int[][] res = new int[people.length][2];

        Arrays.sort(people, (a1, a2) -> {
            if (a1[0] == a2[0]){
                if (a1[1] == a2[1])     return 0;
                return a1[1] < a2[1] ? -1 : 1;
            }else {
                return a1[0] < a2[0] ? -1 : 1;
            }
        });

//        -1代表当前位置为空位
        for (int i = 0; i < res.length; i++) {
            res[i][0] = -1;
        }

        for (int[] person : people) {
            int k = 0;
            for (int i=0; i<res.length; i++){
                if (k == person[1] && res[i][0] == -1){
                    res[i] = person;
                    break;
                }
                if (res[i][0] == -1 || res[i][0] == person[0])   k++;
            }

        }

        return res;
    }

    /**
     * 435. Non-overlapping Intervals
     * pass  42%   97%
     * @param intervals
     * @return
     */
    public int eraseOverlapIntervals(Interval[] intervals) {
        if (intervals.length == 0)  return 0;

        Arrays.sort(intervals,(a1, a2) -> {
            if (a1.end == a2.end){
                return 0;
            }else {
                return a1.end < a2.end ? -1 : 1;
            }
        });

        int pos = intervals[0].end;
        int res = 0;

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i].start < pos){
                res++;
            }else {
                pos = intervals[i].end;
            }
        }

        return res;
    }

    /**
     * 455. Assign Cookies
     * pass
     * @param g
     * @param s
     * @return
     */
    public int findContentChildren(int[] g, int[] s) {
        int res = 0;
        int i = 0, j = 0;
        int m = g.length, n = s.length;

        Arrays.sort(g);
        Arrays.sort(s);

        while (i < m && j < n){
            while (j < n && g[i] > s[j])    j++;
            if (j == n){
                break;
            }
            res++;
            i++;
            j++;
        }

        return res;
    }

    /**
     * 621. Task Scheduler
     * todo
     * @param tasks
     * @param n
     * @return
     */
    public int leastInterval(char[] tasks, int n) {


        return 0;
    }

    /**
     * 659. Split Array into Consecutive Subsequences
     * @param nums
     * @return
     */
    public static boolean isPossible(int[] nums) {
        int total_cnt = 0;
        int curr_cnt;
        while (total_cnt < nums.length){
            curr_cnt = 0;
            for (int i = 0; i < nums.length-1;) {
                if (nums[i] != -1){
                    curr_cnt++;
                    int j = i + 1;
                    while (j < nums.length && (nums[j] == -1 || nums[j] == nums[i])){
                        j++;
                    }
                    if (j == nums.length){
                        break;
                    }
                    i = j;
                    nums[i] = -1;
                }else {
                    i++;
                }
            }
            System.out.println(total_cnt + "---" + curr_cnt);
            if (curr_cnt < 3)   return false;
            total_cnt += curr_cnt;
        }
        return true;
    }

    /**
     * 714. Best Time to Buy and Sell Stock with Transaction Fee
     * Input: prices = [1, 3, 2, 8, 4, 9], fee = 2
     * Output: 8
     * pass 100%  12%
     * todo     思路
     * @param prices
     * @param fee
     * @return
     */
    public int maxProfit(int[] prices, int fee) {
        int profit = 0, hold = prices[0];
        for (int i = 1; i < prices.length; i++) {
            int tmp = prices[i] - hold - fee;
            if (tmp > 0){
                profit += tmp;
                hold = prices[i] - fee;
            }else {
                hold = Math.min(hold, prices[i]);
            }
        }
        return profit;
    }

    /**
     * 738. Monotone Increasing Digits
     * pass    99%     100%
     * @param N
     * @return
     */
    public static int monotoneIncreasingDigits(int N) {
        char[] str = String.valueOf(N).toCharArray();
        for (int i = str.length - 1; i > 0; i--) {
            if (str[i] < str[i-1]){
                for (int j=i; j<str.length; j++)   str[j] = '9';
                str[i-1] -= 1;
            }
        }
        return Integer.valueOf(String.valueOf(str));
    }


    /**
     * 763. Partition Labels
     * pass  96%    85%
     * @param S
     * @return
     */
    public List<Integer> partitionLabels(String S) {
        List<Integer> res = new ArrayList<>();

//        start：子串起始位置，end：子串结束位置
        int start = 0, end = 0;

        while (end < S.length()){
//            求出当前子串每个字符在S中出现的最靠后的位置 j
//            若j > end，则end = j
            for (int i=start; i<=end; i++){
//                从后往前找
                int j = S.length() - 1;
                while (j > end && S.charAt(i) != S.charAt(j))      j--;
                end = j;
            }

            res.add(end - start + 1);

//            更新start和end
            end += 1;
            start = end;

        }

        return res;
    }



    /**
     * 767. Reorganize String
     * pass 5%  5%
     * todo
     * @param S
     * @return
     */
    public static String reorganizeString(String S) {
        LinkedList<Character> chars = new LinkedList<>();
        for (char c : S.toCharArray())  chars.add(c);
        for (int i = 0; i < chars.size()-1; i++) {
            if (chars.get(i) == chars.get(i+1)){
                char tmp = chars.remove(i+1);
                int k = -1;
                for (int j = 0; j < chars.size(); j++) {
                     if (j == 0 || j == chars.size()-1){
                         if (tmp != chars.get(j)){
                             k = j == 0 ? 0 : j + 1;
                         }
                     }else {
                         if (tmp != chars.get(j-1) && tmp != chars.get(j)){
                             k = j;
                         }
                     }
                }
                if (k == -1){
                    return "";
                }
                chars.add(k, tmp);
                i--;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (Object o : chars.toArray()) {
            sb.append(o);
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    /**
     * 860. Lemonade Change
     * pass
     * @param bills
     * @return
     */
    public boolean lemonadeChange(int[] bills) {
        int five = 0;
        int ten = 0;

        for (int bill : bills) {
            if (bill == 5){
                five++;
            }else if (bill == 10){
                if (five == 0){
                    return false;
                }
                five--;
                ten++;
            }else if (bill == 20){
                if (ten == 0){
                    if (five < 3){
                        return false;
                    }
                    five -= 3;
                }else {
                    if (five == 0){
                        return false;
                    }
                    five--;
                    ten--;
                }
            }
        }

        return true;
    }

    /**
     * 861. Score After Flipping Matrix
     * pass     100%    6%
     * 越靠前的列 1 的数目越多，则最终的和越大
     * 1. 对于第一列，若某个值为 0 ，则将当前行toggle
     * 2. 从第二列至最后一列，若当前列 0 的数目大于行数的一半，则toggle当前列
     * @param A
     * @return
     */
    public int matrixScore(int[][] A) {
        int n = A.length;
        int m = A[0].length;

//        处理第一列
        for (int i=0; i<n; i++){
            if (A[i][0] == 0){
                for (int j=0; j<m; j++)   A[i][j] ^= 1;
            }
        }

//        处理第二至m列
        for (int i=1; i<m; i++){
            int zero = 0;
            for (int j=0; j<n; j++){
                if (A[j][i] == 0)   zero++;
            }
            if (zero > n / 2){
                for (int h=0; h<n; h++)     A[h][i] ^= 1;
            }
        }

        int res = 0;
        int k = 1;
//        求A的和
        for (int i=m-1; i>=0; i--){
            for(int j=0; j<n; j++){
                res += k * A[j][i];
            }
            k *= 2;
        }

        return res;
    }

    /**
     * 870. Advantage Shuffle
     * pass  13%  91%
     * @param A
     * @param B
     * @return
     */
    public int[] advantageCount(int[] A, int[] B) {
        int[] res = new int[A.length];
        Arrays.sort(A);
        for (int i = 0; i < B.length; i++) {
            int j = 0;
            while (j < A.length && A[j] <= B[i])    j++;
            if (j != A.length){
                res[i] = A[j];
                A[j] = -1;
            }else {
                j = 0;
                while (j < A.length && A[j] == -1)    j++;
                res[i] = A[j];
                A[j] = -1;
            }
        }
        return res;
    }

    /**
     * 881. Boats to Save People
     * pass
     * @param people
     * @param limit
     * @return
     */
    public int numRescueBoats(int[] people, int limit) {
        int res = 0;
//        自定义排序比较方法，不能用于基本数据类型
        Arrays.sort(people);
        int i = 0, j = people.length - 1;
        while (i <= j){
            res++;
            if (people[i] + people[j] <= limit)     i++;
            j--;
        }
        return res;
    }

    /**
     * 921. Minimum Add to Make Parentheses Valid
     * pass
     * @param S
     * @return
     */
    public int minAddToMakeValid(String S) {
        Stack<Integer> help = new Stack<>();

        for (int i=0, n=S.length(); i<n; i++){
            if (S.charAt(i) == '('){
                help.push(0);
            }else{
                if (help.empty()){
                    help.push(1);
                }else {
                    if (help.peek() == 0){
                        help.pop();
                    }else {
                        help.push(1);
                    }
                }
            }
        }

        return help.size();
    }

    public static void main(String[] args) {
        int X = 1;
        int Y = 1000000000;
        brokenCalc(X, Y);
    }

    /**
     * 991. Broken Calculator
     * pass
     * @param X
     * @param Y
     * @return
     */
    public static int brokenCalc(int X, int Y) {
        int res = 0;
        while (Y > X){
            res++;
            if (Y % 2 == 1)
                Y++;
            else
                Y /= 2;
        }
        return res + X - Y;
    }

    /**
     * 1005. Maximize Sum Of Array After K Negations
     * pass
     * @param A
     * @param K
     * @return
     */
    public int largestSumAfterKNegations(int[] A, int K) {
        int minPos = 0;
        int maxSum = 0;

        while (K-- > 0){
            for (int i = 0; i < A.length; i++) {
                if (A[i] < A[minPos]){
                    minPos = i;
                }
            }
            A[minPos] = -A[minPos];
        }

        for (int i : A)    maxSum += i;

        return maxSum;
    }


}
