import java.util.Scanner;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {
        System.out.println("Введите выржание: ");
        System.out.println(calc());
    }
    public static String calc() {
        Scanner scanner = new Scanner(System.in);
        Converter converter = new Converter();
        String[] operation = {"+", "-", "*", "/"};
        String[] operationForRegular = {"\\+", "-", "\\*", "/"};
        String expression = scanner.nextLine();

        int indexOperation = -1;
        //Запоминаем индекс операции
        for (int i = 0; i < operation.length; i++) {
            if (expression.contains(operation[i])) {
                indexOperation = i;
                break;
            }
        }

        if (indexOperation == -1) {
            throw new RuntimeException("Неверная арифметическая операция!");
        }
        //Убираем пробелы,поднимаем в верхний регистри разбиваем строку на массив по символу операции
        String[] numbersArrays = expression.replaceAll("\\s", "").toUpperCase().split(operationForRegular[indexOperation]);

        int a, b;
        boolean isRoman = converter.isRomanNumber(numbersArrays[0]);
        //Если форматы чисел равны друг другу,то конвертируем
        if (converter.isRomanNumber(numbersArrays[0]) == converter.isRomanNumber(numbersArrays[1])) {
            if (isRoman) {
                a = converter.romanToArabian(numbersArrays[0]);
                b = converter.romanToArabian(numbersArrays[1]);
            } else {
                a = Integer.parseInt(numbersArrays[0]);
                b = Integer.parseInt(numbersArrays[1]);
            }
        } else throw new RuntimeException("Разные форматы значений!");

        if ((a > 10 || b > 10)) {
            throw new RuntimeException("Числа должны быть в диапозоне [0-10]!");
        }

        int result = 0;
        //Выполняем подсчет
        switch (operation[indexOperation]) {
            case "+" -> result = a + b;
            case "-" -> result = a - b;
            case "/" -> result = a / b;
            case "*" -> result = a * b;
        }
        //Возврат строки со значением
        if (isRoman) {
            return converter.numberToRoman(result);
        } else return Integer.toString(result);
    }
    static class Converter {
        TreeMap<Character, Integer> romanMap = new TreeMap<>();
        TreeMap<Integer, String> arabianMap = new TreeMap<>();

        public Converter() {
            romanMap.put('I', 1);
            romanMap.put('V', 5);
            romanMap.put('X', 10);
            romanMap.put('L', 50);
            romanMap.put('C', 100);

            arabianMap.put(100, "C");
            arabianMap.put(90, "XC");
            arabianMap.put(50, "L");
            arabianMap.put(40, "XL");
            arabianMap.put(10, "X");
            arabianMap.put(9, "IX");
            arabianMap.put(5, "V");
            arabianMap.put(4, "IV");
            arabianMap.put(1, "I");
        }

        //Проверка на римское ли число
        public boolean isRomanNumber(String number) {
            int count = 0;
            for (int i = 0; i < number.length(); i++) {
                if (romanMap.containsKey(number.charAt(i))) {
                    count += 1;
                } else {
                    if (count > 0 && count != number.length()) {
                        throw new RuntimeException("Неверный формат римского числа!");
                    }
                }
            }
            if (count == number.length()) {
                return true;
            } else {
                if (number.matches("^[0-9]+$")) {
                    return false;
                } else {
                    throw new RuntimeException("Неверный формат арабского числа!");
                }
            }
        }

        //Преобразование арабского числа в римское
        public String numberToRoman(int number) {
            if (number < 1)
                throw new RuntimeException("Нет римкого предсталвения числа по значению меньше одного!");
            String roman = "";
            int arabianKey;
            do {
                arabianKey = arabianMap.floorKey(number);
                roman += arabianMap.get(arabianKey);
                number -= arabianKey;
            } while (number != 0);
            return roman;
        }

        public int romanToArabian(String romanNumber) {
            char[] arraysSymbols = romanNumber.toCharArray();
            int arabian;
            int result = romanMap.get(arraysSymbols[romanNumber.length() - 1]);
            for (int i = romanNumber.length() - 2; i >= 0; i--) {
                arabian = romanMap.get(arraysSymbols[i]);
                if (arabian < romanMap.get(arraysSymbols[i + 1])) {
                    result -= arabian;
                } else result += arabian;
            }
            return result;
        }
    }
}




