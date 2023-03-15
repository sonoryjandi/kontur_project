package ru.amalysheva;

import java.util.*;

/**
 * Класс содержит основную логику игры "Быки и коровы", проверки листов и массивов,
 * генерацию случайных чисел и логику взаимодействия с пользователем через консоль.
 *
 * @author Malysheva Anastasia
 */
public class Application {
    private static final Scanner scanner = new Scanner(System.in);
    private final static int listCapacity = 4;
    private final List<Integer> listToGuess = new ArrayList<>(listCapacity);
    private final List<Integer> usersList = new ArrayList<>(listCapacity);

    /**
     * Метод запускает основную логику игры, генерацию листа угадываемых чисел, метод сравнения двух листов.
     */
    public void start() {
        createListToGuess();
        System.out.println("""
                Быки и коровы.
                Вам нужно угадать четырёхзначное число (числа не повторяются).
                Если ваше число есть в загаданном числе - это корова.
                Если ваше число есть в загаданном числе и стоит на том же месте - это бык.
                Вам нужно отгадать все четыре числа.""");
        bullsAndCowsAlgorithm(listToGuess, usersList);
    }

    /**
     * Метод заполняет лист listToGuess набором случайных чисел и перемешивает их.
     */
    private void createListToGuess() {
        listToGuess.addAll(createRandomIntegers(listCapacity));
        Collections.shuffle(listToGuess);
    }

    /**
     * Метод создаёт сет рандомных чисел заданного размера. Сет используется во избежание дубликатов.
     *
     * @param size требуемый размер сета
     */
    private Set<Integer> createRandomIntegers(int size) {
        Set<Integer> setOfNumbers = new HashSet<>();
        Random random = new Random();
        while (setOfNumbers.size() < size) {
            setOfNumbers.add(random.nextInt(9 - 1) + 1);
        }
        return setOfNumbers;
    }

    /**
     * Метод запускает цикл игры, инициализирует лист пользователя, сранивает его с загаданным:
     * если элемент gameList равен элементу playersList и их индексы равны - увеличивается значение bull;
     * если элемент gameList равен элементу playersList, но их индексы не равны - увеличивается значение cow;
     * Если к концу итерации цикла bull != listCapacity, выводится значение bull и cow, цикл продолжается.
     * Если bull == listCapacity, цикл прерывается.
     *
     * @param gameList    лист, генерируемый игрой
     * @param playersList лист, генерируемый пользователем
     */
    private void bullsAndCowsAlgorithm(List<Integer> gameList, List<Integer> playersList) {
        while (true) {
            usersListInitialisation();
            int cow = 0;
            int bull = 0;
            for (int i = 0; i < listCapacity; i++) {
                for (int j = 0; j < listCapacity; j++) {
                    if ((gameList.get(i)).equals(playersList.get(j))) {
                        if (i == j) {
                            bull++;
                        } else {
                            cow++;
                        }
                    }
                }
            }
            if (bull != listCapacity) {
                System.out.println("Коров: " + cow + " Быков: " + bull);
                playersList.clear();
                continue;
            }
            System.out.println("Вы угадали все числа! Правильная комбинация: " + usersList);
            break;
        }
    }

    /**
     * Метод инициализирует массив игрока, проверяет его на недопустимые символов,
     * количество символов и наличие дубликатов. В случае прохождения проверок, записывает
     * данные массива в usersList. В случае не прохождения проверок, лист очищается, генерируется заново.
     *
     * @throws NumberFormatException в случае, если введены любые символы, кроме чисел
     */
    private void usersListInitialisation() {
        while (true) {
            try {
                String[] usersArray = scanner.nextLine().split("(?<=.)");
                if (isArrayBounceCorrect(usersArray) && isNoDuplicatesArray(usersArray)) {
                    for (int i = 0; i < listCapacity; i++) {
                        usersList.add(Integer.valueOf(usersArray[i]));
                    }
                    return;
                }
            } catch (NumberFormatException exception) {
                System.out.println("Ваш ввод содержит недопустимые символы. Введите числа.");
                usersList.clear();
            }
        }
    }

    /**
     * Метод проверяет входящий массив на соответствие условиям длины.
     *
     * @param usersArray проверяемый массив
     */
    private boolean isArrayBounceCorrect(String[] usersArray) {
        if (usersArray.length < listCapacity) {
            System.out.println("Недостаточно символов.");
            return false;
        }
        if (usersArray.length > listCapacity) {
            System.out.println("Слишком много символов.");
            return false;
        }
        return true;
    }

    /**
     * Метод проверяет входящий массив на дубликаты.
     *
     * @param arrayToCheck проверяемый массив
     */
    private boolean isNoDuplicatesArray(String[] arrayToCheck) {
        Set<String> setOfDuplicates = new HashSet<>(Arrays.asList(arrayToCheck));
        if (arrayToCheck.length == setOfDuplicates.size()) {
            return true;
        }
        System.out.println("Ваш ввод содержит дубликаты");
        return false;
    }
}