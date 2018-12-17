import java.util.ArrayList;
import java.util.List;

class StringCalculator {

    private final int UPPER_BOUND = 1000;

    int Add(String inputString) {
        int result = 0;

        if (inputString.isEmpty()) {
            return result;
        }

        try {
            if (customDelimiter(inputString)) {
                String delimiter = getCustomDelimiterRegEx(inputString);
                String numbers = getNumbersFromString(inputString);
                result = addNumbers(numbers.split(delimiter));
            } else {
                result = addNumbers(inputString.split(","));
            }
        } catch (NumberFormatException e) {
            System.out.println("Error - Input string not properly parsed by custom delimiter. Check the RegEx.");
            return -1;

        } catch (Exception e) {
            System.out.println(e);
            return -1;
        }

        return result;
    }

    protected int addNumbers(String[] numbers) throws Exception {
        List<Integer> invalidNegativeNumbers = new ArrayList<>();
        int currentSum = 0;

        for (String number : numbers) {
            Integer n = Integer.parseInt(number);
            if (belowUpperBound(n)) {
                if (positiveNumber(n)) {
                    currentSum += n;
                } else {
                    invalidNegativeNumbers.add(n);
                }
            }
        }

        if (!invalidNegativeNumbers.isEmpty()) {
            throw new Exception("Calculation Failed - Negatives not allowed. Remove the following number(s): " + invalidNegativeNumbers.toString());
        }

        return currentSum;
    }

    protected String getCustomDelimiterRegEx(String inputString) {
        String delimiters = inputString.substring(inputString.indexOf("/") + 2, inputString.indexOf('\n'));
        StringBuilder regex = new StringBuilder();

        regex.append("[");
        for (int i = 0; i < delimiters.length(); i++) {
            regex.append("\\").append(delimiters.charAt(i));
        }
        regex.append("]+");

        return regex.toString();
    }

    protected String getNumbersFromString(String inputString) {
        return inputString.substring(inputString.indexOf('\n') + 1, inputString.length());
    }

    private boolean customDelimiter(String inputString) {
        return (inputString.charAt(0) == '/');
    }

    private boolean positiveNumber(int n) {
        return (n > 0);
    }

    private boolean belowUpperBound(int n) {
        return (n <= UPPER_BOUND);
    }

}
