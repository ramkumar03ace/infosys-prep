public class q7 {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("          JAVA CONVERSION DEMONSTRATION          ");
        System.out.println("==================================================\n");

        /* =========================================================================
         * 1. STRING <-> INT
         * =========================================================================
         * String to int:
         * - Integer.parseInt(str): Returns primitive int. Throws NumberFormatException if invalid.
         * - Integer.valueOf(str): Returns Integer wrapper Object. Uses internal cache for [-128, 127].
         * DIFFERENCE: Use parseInt() for primitive math; use valueOf() when requiring an Object (e.g. List<Integer>).
         * 
         * int to String:
         * - String.valueOf(num): Overloaded method. Safe for null objects (returns "null").
         * - Integer.toString(num): Specific to integers. Converts int directly to String.
         * DIFFERENCE: String.valueOf(x) internally calls Integer.toString(x) for ints.
         */
        System.out.println("--- 1. String <-> int ---");
        String strNum = "123";
        int parsedInt = Integer.parseInt(strNum); 
        int valueOfInt = Integer.valueOf(strNum);  
        System.out.println("String \"" + strNum + "\" to int (parseInt): " + parsedInt);
        System.out.println("String \"" + strNum + "\" to Integer (valueOf): " + valueOfInt);

        int num = 456;
        String intToStr1 = String.valueOf(num);
        String intToStr2 = Integer.toString(num);
        System.out.println("int " + num + " to String (String.valueOf): \"" + intToStr1 + "\"");
        System.out.println("int " + num + " to String (Integer.toString): \"" + intToStr2 + "\"\n");


        /* =========================================================================
         * 2. CHAR <-> STRING
         * =========================================================================
         * char to String:
         * - Character.toString(ch): Wrapper class helper method.
         * - String.valueOf(ch): Standard utility method.
         * - "" + ch: String concatenation.
         * DIFFERENCE: String.valueOf(ch) / Character.toString(ch) are cleaner and faster in loops than "" + ch.
         * 
         * String to char:
         * - str.charAt(index): Extracts char at 0-based index. Throws IndexOutOfBoundsException if invalid.
         */
        System.out.println("--- 2. char <-> String ---");
        char ch = 'A';
        String charToStr1 = Character.toString(ch);
        String charToStr2 = String.valueOf(ch);
        String charToStr3 = "" + ch;
        System.out.println("char '" + ch + "' to String (Character.toString): \"" + charToStr1 + "\"");
        System.out.println("char '" + ch + "' to String (String.valueOf): \"" + charToStr2 + "\"");
        System.out.println("char '" + ch + "' to String (concatenation): \"" + charToStr3 + "\"");

        String word = "Hello";
        char strToChar = word.charAt(0);
        System.out.println("First character of \"" + word + "\" is: '" + strToChar + "'\n");


        /* =========================================================================
         * 3. STRING <-> CHAR ARRAY
         * =========================================================================
         * String to char[]:
         * - str.toCharArray(): Allocates a new char array with string contents.
         * NOTE: Modifying the returned array will not affect the original String (Strings are immutable).
         * 
         * char[] to String:
         * - new String(charArr): Creates a new String object from array.
         * - String.valueOf(charArr): Calls new String(charArr) internally.
         */
        System.out.println("--- 3. String <-> char[] ---");
        String text = "Java";
        char[] charArray = text.toCharArray();
        System.out.print("String \"" + text + "\" to char array: [");
        for (int i = 0; i < charArray.length; i++) {
            System.out.print("'" + charArray[i] + "'" + (i < charArray.length - 1 ? ", " : ""));
        }
        System.out.println("]");

        char[] helloArr = {'H', 'e', 'l', 'l', 'o'};
        String arrToString1 = new String(helloArr);
        String arrToString2 = String.valueOf(helloArr);
        System.out.println("char[] to String (constructor): \"" + arrToString1 + "\"");
        System.out.println("char[] to String (String.valueOf): \"" + arrToString2 + "\"\n");


        /* =========================================================================
         * 4. INT <-> BASE REPRESENTATIONS (BINARY, OCTAL, HEX)
         * =========================================================================
         * Decimal to Base String:
         * - Integer.toBinaryString(n): Converts int to binary string (Base 2).
         * - Integer.toOctalString(n): Converts int to octal string (Base 8).
         * - Integer.toHexString(n): Converts int to hex string (Base 16).
         * 
         * Base String to Decimal:
         * - Integer.parseInt(str, radix): Parses string in base 'radix' to base 10 int.
         */
        System.out.println("--- 4. int <-> Binary, Octal, Hex ---");
        int decimalNum = 255;
        String binaryStr = Integer.toBinaryString(decimalNum);
        String octalStr = Integer.toOctalString(decimalNum);
        String hexStr = Integer.toHexString(decimalNum);
        System.out.println("Decimal " + decimalNum + " in Binary: " + binaryStr);
        System.out.println("Decimal " + decimalNum + " in Octal: " + octalStr);
        System.out.println("Decimal " + decimalNum + " in Hexadecimal: " + hexStr);

        int fromBinary = Integer.parseInt(binaryStr, 2);
        int fromOctal = Integer.parseInt(octalStr, 8);
        int fromHex = Integer.parseInt(hexStr, 16);
        System.out.println("Binary \"" + binaryStr + "\" back to decimal: " + fromBinary);
        System.out.println("Octal \"" + octalStr + "\" back to decimal: " + fromOctal);
        System.out.println("Hexadecimal \"" + hexStr + "\" back to decimal: " + fromHex + "\n");


        /* =========================================================================
         * 5. CHAR <-> ASCII VALUE
         * =========================================================================
         * char to ASCII int:
         * - (int) charVal: Implicit/explicit cast converts char to its UTF-16/ASCII integer code.
         * 
         * ASCII int to char:
         * - (char) asciiVal: Explicit narrowing cast converts integer code back to character.
         */
        System.out.println("--- 5. char <-> ASCII Value ---");
        char asciiChar = 'Z';
        int asciiValue = (int) asciiChar;
        System.out.println("Character '" + asciiChar + "' has ASCII value: " + asciiValue);

        int code = 97;
        char codeChar = (char) code;
        System.out.println("ASCII value " + code + " corresponds to Character: '" + codeChar + "'\n");


        /* =========================================================================
         * 6. OTHER COMMON CONVERSIONS (DOUBLE / BOOLEAN)
         * =========================================================================
         * String to Double: Double.parseDouble(str)
         * String to Boolean: Boolean.parseBoolean(str)
         * GOTCHA: Boolean.parseBoolean(str) NEVER throws an exception. It returns true ONLY 
         * if the string equalsIgnoreCase("true"). For any other text ("yes", "1", null), it returns false.
         */
        System.out.println("--- 6. Other Common Conversions ---");
        String doubleStr = "3.14159";
        double parsedDouble = Double.parseDouble(doubleStr);
        System.out.println("String \"" + doubleStr + "\" to double: " + parsedDouble);
        System.out.println("double " + parsedDouble + " to String: \"" + String.valueOf(parsedDouble) + "\"");

        String boolStr = "true";
        boolean parsedBool = Boolean.parseBoolean(boolStr);
        System.out.println("String \"" + boolStr + "\" to boolean: " + parsedBool);
        System.out.println("boolean " + parsedBool + " to String: \"" + String.valueOf(parsedBool) + "\"");
        
        System.out.println("==================================================");
    }
}
