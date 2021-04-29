package com.SGY.c_utils.content;

public class SGYNumberUtils {
    /**
     * 转换数字以万为单位
     *
     * @param num     要转化的数字
     * @param digit   保留的位数 可传null
     * @param isRound 是否四舍五入
     * @return
     */
    public static String conversionToWan(int num, Integer digit, boolean isRound) {
        if (num < 10000) {
            return num + "";
        }
        String unit = "万";
        double newNum = num / 10000.0;
        if (isRound) {
            roundForSmall(newNum, digit);
        }
        if (digit != null) {
            String numStr = String.format("%." + digit + "f", newNum);
            return numStr + unit;
        }
        return newNum + unit;
    }

    /**
     * 转换数字以万为单位
     *
     * @param num     要转化的数字
     * @param digit   保留的位数 可传null
     * @param isRound 是否四舍五入
     * @return
     */
    public static String conversionToW(int num, Integer digit, boolean isRound) {
        if (num < 10000) {
            return num + "";
        }
        String unit = "w";
        double newNum = num / 10000.0;
        if (isRound) {
            roundForSmall(newNum, digit);
        }
        if (digit != null) {
            String numStr = String.format("%." + digit + "f", newNum);
            return numStr + unit;
        }
        return newNum + unit;
    }

    /**
     * 小数点后的四舍五入
     *
     * @param newNum 要四舍五入的数据
     * @param digit  保留几位
     * @return
     */
    private static double roundForSmall(double newNum, Integer digit) {
        int newNumInt = (int) newNum;
        double smallNum = newNum - newNumInt;
        smallNum = Math.round(smallNum * digit * 10) / 10 / digit;
        newNum = smallNum + newNumInt;
        return newNum;
    }

    /**
     * 数组
     *
     * @param arr       数组
     * @param sum       总数
     * @param idx       索引
     * @param precision 精度
     * @return
     */
    public static double getPercentValue(int[] arr, double sum, int idx, int precision) {
        if ((arr.length - 1) < idx) {
            return 0;
        }
        //求和
        if (sum <= 0) {
            for (int i = 0; i < arr.length; i++) {
                sum += arr[i];
            }
        }
        //10的2次幂是100，用于计算精度。
        double digits = Math.pow(10, precision);
        //扩大比例100
        double[] votesPerQuota = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            double val = arr[i] / sum * digits * 100;
            votesPerQuota[i] = val;
        }
        //总数,扩大比例意味的总数要扩大
        double targetSeats = digits * 100;
        //再向下取值，组成数组
        double[] seats = new double[arr.length];
        for (int i = 0; i < votesPerQuota.length; i++) {
            seats[i] = Math.floor(votesPerQuota[i]);
        }
        //再新计算合计，用于判断与总数量是否相同,相同则占比会100%
        double currentSum = 0;
        for (int i = 0; i < seats.length; i++) {
            currentSum += seats[i];
        }
        //余数部分的数组:原先数组减去向下取值的数组,得到余数部分的数组
        double[] remainder = new double[arr.length];
        for (int i = 0; i < seats.length; i++) {
            remainder[i] = votesPerQuota[i] - seats[i];
        }
        while (currentSum < targetSeats) {
            double max = 0;
            int maxId = 0;
            int len = 0;
            for (int i = 0; i < remainder.length; ++i) {
                if (remainder[i] > max) {
                    max = remainder[i];
                    maxId = i;
                }
            }
            //对最大项余额加1
            ++seats[maxId];
            //已经增加最大余数加1,则下次判断就可以不需要再判断这个余额数。
            remainder[maxId] = 0;
            //总的也要加1,为了判断是否总数是否相同,跳出循环。
            ++currentSum;
        }
        // 这时候的seats就会总数占比会100%
        return seats[idx] / digits;
    }
}
