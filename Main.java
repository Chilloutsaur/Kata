package Kata_Calc_Test;

import java.util.TreeMap;

class Main {
    public static String calc(String input) {
        String[] operations = {"+", "-", "/", "*"};
        String[] regex = {"\\+", "-", "/", "\\*"};

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
            return null;
        }

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


            int result = switch (operations[index]) {
                case "+" -> a + b;
                case "-" -> a - b;
                case "*" -> a * b;
                default -> a / b;
            };


            // Возврат в соответствующем формате:
            if (isRoman) {
                System.out.println(converter.intToRoman(result));
            } else {
                System.out.println(result);
            }
        } else {
            System.out.println("Ошибка. Разные форматы");
        }
        return null;
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
            int end = s.length() - 1;
            char[] arr = s.toCharArray();
            int arabian;
            int result = romanKeyMap.get(arr[end]);
            for (int i = end - 1; i >= 0; i--) {
                arabian = romanKeyMap.get(arr[i]);

                if (arabian < romanKeyMap.get(arr[i + 1])) {
                    result -= arabian;
                } else {
                    result += arabian;
                }


            }
            return result;
        }
    }
}