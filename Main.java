package Kata_Calc_Test;

import java.util.TreeMap;
import java.util.Scanner;

class Main2 {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение:");
        String str = scanner.nextLine();
        System.out.println(calc(str));

    }

    public static String calc(String input) throws Exception {
        String[] operations = {"+", "-", "/", "*"};
        String[] regex = {"\\+", "-", "/", "\\*"};

        String[] operands = input.split("[+\\-*/]");
        if (operands.length != 2) {
            throw new Exception("Должно быть два операнда");
        }

        // определение операции
        int index = -1;
        for (int i = 0; i < operations.length; i++) {
            if (input.contains(operations[i])) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("операция не найдена");
        }
        String fResult;

        //деление строки по знаку:
        String[] spl = input.split(regex[index]);

        // Начало конвертации:
        Converter converter = new Converter();
        //Проверка одинакового формата:
        if (converter.isRoman(spl[0]) == converter.isRoman(spl[1])) {
            int a, b;
            //Проверка формата:
            boolean isRoman = converter.isRoman(spl[0]);
            if (isRoman) {
                a = converter.romanToInt(spl[0]);
                b = converter.romanToInt(spl[1]);
            } else {
                a = Integer.parseInt(spl[0]);
                b = Integer.parseInt(spl[1]);
            }

            if (a > 10 || b > 10) {
                throw new Exception("Числа должны быть от 1 до 10");
            }

            int option = switch (operations[index]) {

                case "+" -> a + b;
                case "-" -> a - b;
                case "*" -> a * b;
                default -> a / b;
            };


            // Возврат в соответствующем формате:
            if (isRoman && option == 0) {
                throw new Exception("В римских числах нет 0");
            }
            if (isRoman) {
                return converter.intToRoman(option);
            } else {
                fResult = String.valueOf(option);
                return fResult;
            }
        } else {
            throw new Exception("Разные форматы");
        }
    }


    static class Converter {
        TreeMap<Character, Integer> romanKeyMap = new TreeMap<>();
        TreeMap<Integer, String> arabianKeyMap = new TreeMap<>();

        Converter() {
            romanKeyMap.put('I', 1);
            romanKeyMap.put('V', 5);
            romanKeyMap.put('X', 10);
            romanKeyMap.put('L', 50);
            romanKeyMap.put('C', 100);

            arabianKeyMap.put(100, "C");
            arabianKeyMap.put(90, "XC");
            arabianKeyMap.put(50, "L");
            arabianKeyMap.put(40, "XL");
            arabianKeyMap.put(10, "X");
            arabianKeyMap.put(9, "IX");
            arabianKeyMap.put(5, "V");
            arabianKeyMap.put(4, "IV");
            arabianKeyMap.put(1, "I");

        }


        boolean isRoman(String number) {
            return romanKeyMap.containsKey(number.charAt(0));
        }


        String intToRoman(int number) {
            StringBuilder roman = new StringBuilder();
            int arabianKey;
            do {
                arabianKey = arabianKeyMap.floorKey(number);
                roman.append(arabianKeyMap.get(arabianKey));
                number -= arabianKey;
            } while (number != 0);
            return roman.toString();
        }

        int romanToInt(String s) {
            String[] romanArray = new String[]{"0", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
            boolean rim = false;

            for (String value : romanArray) {
                if (s.equals(value)) {
                    rim = true;
                    break;
                }
            }

            if (rim) {
                int end = s.length() - 1;
                char[] arr = s.toCharArray();
                int arabian;
                int result = romanKeyMap.get(arr[end]);
                for (int y = end - 1; y >= 0; y--) {
                    arabian = romanKeyMap.get(arr[y]);
                    if (arabian < romanKeyMap.get(arr[y + 1])) {
                        result -= arabian;
                    } else {
                        result += arabian;
                    }
                }
                return result;
            } else {
                throw new RuntimeException("Некорректное римское число");
            }
        }
    }
}
