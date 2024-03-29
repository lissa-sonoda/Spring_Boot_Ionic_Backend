package com.educandoweb.cursomc.services.validation.utils;

// Fonte: https://gist.github.com/adrianoluis/5043397d378ae506d87366abb0ab4e30

public class BR {
    // CPF
    private static final int[] weightSsn = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    // CNPJ
    private static final int[] weightEin = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    private static int sum(int[] weight, char[] numbers, int length) {
        if (length <= 0) return 0;
        final int nIdx = length - 1;
        final int wIdx = weight.length > numbers.length ? length : nIdx;
        return (sum(weight, numbers, nIdx) + Character.getNumericValue(numbers[nIdx]) * weight[wIdx]);
    }

    private static int calculate(final String document, final int[] weight) {
        final char[] numbers = document.toCharArray();
        int sum = sum(weight, numbers, numbers.length);
        sum = 11 - (sum % 11);
        return sum > 9 ? 0 : sum;
    }

    private static boolean check(String tfn, int length, int[] weight) {
        final String number = tfn.substring(0, length);
        final int digit1 = calculate(number, weight);
        final int digit2 = calculate(number + digit1, weight);
        return tfn.equals(number + digit1 + digit2);
    }

    /**
     * Valida CPF
     */
    public static boolean isValidSsn(String ssn) {
        if (ssn == null || !ssn.matches("\\d{11}") || ssn.matches(ssn.charAt(0) + "{11}")) return false;
        return check(ssn, 9, weightSsn);
    }

    /**
     * Valida CNPJ
     */
    public static boolean isValidEin(String ein) {
        if (ein == null || !ein.matches("\\d{14}")) return false;
        return check(ein, 12, weightEin);
    }
}