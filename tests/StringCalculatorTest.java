import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.fail;

public class StringCalculatorTest {

    private final int ERROR_CODE = -1;
    private final int EMPTY_STRING_CODE = 0;

    private StringCalculator stringCalculator = new StringCalculator();

    @Test
    public void testConstructedRegExOnSplitMethod() {
        String delimiter = stringCalculator.getCustomDelimiterRegEx("//$,@\n");
        String inputString = "1$,@2$,@3$,@";

        String[] numbers = inputString.split(delimiter);

        Assert.assertEquals(Integer.parseInt(numbers[0]), 1);
        Assert.assertEquals(Integer.parseInt(numbers[1]), 2);
        Assert.assertEquals(Integer.parseInt(numbers[2]), 3);
    }

    @Test
    public void testExtractNumbersFromInputString() {
        String inputString = "\n1,2,3,4,5";
        String result = stringCalculator.getNumbersFromString(inputString);

        Assert.assertEquals(result, "1,2,3,4,5");
    }

    @Test
    public void testAddStringNumbers() {
        String[] numbers = {"1", "2", "3"};
        try {
            int result = stringCalculator.addNumbers(numbers);
            Assert.assertEquals(result, 6);
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void testEmptyString() {
        int result = stringCalculator.Add("");
        Assert.assertEquals(EMPTY_STRING_CODE, result);
    }

    @Test
    public void testDefaultDelimiter() {
        int result = stringCalculator.Add("1,2,3");
        Assert.assertEquals(6, result);
    }

    @Test
    public void testDefaultDelimiterWithValueTooLarge() {
        int result = stringCalculator.Add("1,2,3,1001,4");
        Assert.assertEquals(10, result);
    }

    @Test
    public void testDefaultDelimiterWithMultipleNegativeNumber() {
        int result = stringCalculator.Add("1,-2,3,-4");
        Assert.assertEquals(ERROR_CODE, result);
    }

    @Test
    public void testCustomSingleDelimiterOne() {
        int result = stringCalculator.Add("//$\n1$2$3");
        Assert.assertEquals(6, result);
    }

    @Test
    public void testCustomCombinedDelimiter() {
        int result = stringCalculator.Add("//$*\n1$*2$*3$*4");
        Assert.assertEquals(10, result);
    }

    @Test
    public void testCustomCombinedDelimiterMixedUse() {
        int result = stringCalculator.Add("//$,@\n1$2@3,4");
        Assert.assertEquals(10, result);
    }

    @Test
    public void testCustomCombinedDelimiterMixedUseArbitraryLength() {
        int result = stringCalculator.Add("//$,@\n1$$$$2@@3,4$@,5");
        Assert.assertEquals(15, result);
    }

    @Test
    public void testCustomMixedDelimiterArbitraryLength() {
        int result = stringCalculator.Add("//$,@\n1$$,2@@3,4$,@5");
        Assert.assertEquals(15, result);
    }
}
