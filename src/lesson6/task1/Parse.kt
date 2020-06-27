@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    val parts = str.split(" ")
    if (parts.size != 3) return ""

    val year = try {
        parts[2].toInt()
    } catch (e: NumberFormatException) {
        return ""
    }
    if (year < 0) return ""

    val months = mapOf(
        "января" to 1,
        "февраля" to 2,
        "марта" to 3,
        "апреля" to 4,
        "мая" to 5,
        "июня" to 6,
        "июля" to 7,
        "августа" to 8,
        "сентября" to 9,
        "октября" to 10,
        "ноября" to 11,
        "декабря" to 12
    )
    val month = months[parts[1]] ?: 0
    if (month == 0) return ""

    val day = try {
        parts[0].toInt()
    } catch (e: NumberFormatException) {
        return ""
    }
    if (day !in 1..daysInMonth(month, year)) return ""
    return String.format("%02d.%02d.%d", day, month, year)
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val parts = digital.split(".")
    if (parts.size != 3) return ""

    val year = try {
        parts[2].toInt()
    } catch (e: NumberFormatException) {
        return ""
    }
    if (year < 0) return ""

    val months = mapOf(
        1 to "января",
        2 to "февраля",
        3 to "марта",
        4 to "апреля",
        5 to "мая",
        6 to "июня",
        7 to "июля",
        8 to "августа",
        9 to "сентября",
        10 to "октября",
        11 to "ноября",
        12 to "декабря"
    )
    val i = try {
        parts[1].toInt()
    } catch (e: NumberFormatException) {
        return ""
    }
    val month = months[i] ?: ""
    if (month == "") return ""

    val day = try {
        parts[0].toInt()
    } catch (e: NumberFormatException) {
        return ""
    }
    if (day !in 1..daysInMonth(i, year)) return ""
    return String.format("%d %s %d", day, month, year)
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String {
    var chars = phone.split("")
    chars = chars.filter { it != "" && it != " " && it != "-" }
    var result = ""
    if (chars.size > 1 && chars[0] == "+" && chars[1] != "(") {
        result += "+"
        chars = chars - "+"
    }
    if ("(" in chars && ")" in chars) {
        if (chars.indexOf(")") - chars.indexOf("(") > 1) {
            chars = chars - "("
            chars = chars - ")"
        }
    }
    if (chars.isEmpty()) return ""
    for (digit in chars) {
        try {
            digit.toInt()
        } catch (e: NumberFormatException) {
            return ""
        }
        result += digit
    }
    return result
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    var details = jumps.split(" ")
    details = details.filter { it != "%" && it != "-" }
    if (details.isEmpty()) return -1
    val lengths = mutableListOf<Int>()
    for (length in details) {
        val l = try {
            length.toInt()
        } catch (e: NumberFormatException) {
            return -1
        }
        if (l > 0) lengths.add(l) else return -1
    }
    return lengths.max() ?: -1
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    val details = jumps.split(" ")
    if (details.isEmpty() || details.size % 2 != 0) return -1
    val results = mutableMapOf<Int, String>()
    for (i in details.indices step 2) {
        val height = try {
            details[i].toInt()
        } catch (e: NumberFormatException) {
            return -1
        }
        val tries = details[i + 1]
        if (height < 1 || height in results || tries.any { it !in listOf('%', '-', '+') }) return -1
        results[height] = tries
    }
    val heightToRemove = mutableListOf<Int>()
    for ((height, tries) in results) {
        if ('+' !in tries) {
            heightToRemove.add(height)
        }
    }
    results -= heightToRemove
    return if (results.isEmpty()) -1 else results.keys.max() ?: -1
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    val list = expression.split(" ")
    if (list.isEmpty() || list.size % 2 == 0) throw IllegalArgumentException()
    val terms = mutableListOf<Int>()
    for (i in list.indices step 2) {
        val x = list[i]
        if (x.startsWith('+') || x.startsWith('-')) throw IllegalArgumentException()
        val number = try {
            x.toInt()
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException()
        }
        val sign = if (i == 0)
            1
        else when (list[i - 1]) {
            "+" -> 1
            "-" -> -1
            else -> throw IllegalArgumentException()
        }
        terms.add(number * sign)
    }
    return terms.sum()
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val strLower = str.toLowerCase()
    val words = strLower.split(" ")
    if (words.size < 2) return -1
    var wordNum = -1
    for (i in 0..(words.size - 2)) {
        if (words[i] == words[i + 1]) {
            wordNum = i
            break
        }
    }
    if (wordNum == -1) return -1
    if (wordNum == 0) return 0
    var index = 0
    for (i in 0 until wordNum) {
        index += (words[i].length + 1)
    }
    return index
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    val pairs = description.trim().split(";")
    if (pairs.isEmpty()) return ""
    var goodsMap = mutableMapOf<String, Double>()
    for (goods in pairs) {
        val pair = goods.trim().split(" ")
        if (pair.size != 2) return ""
        val name = pair[0]
        val price = try {
            pair[1].toDouble()
        } catch (e: NumberFormatException) {
            return ""
        }
        if (price < 0.0) return ""
        if (name in goodsMap) {
            goodsMap[name] = maxOf(price, goodsMap[name]!!)
        } else goodsMap[pair[0]] = price
    }
    val maxPrice = goodsMap.values.max()
    goodsMap = goodsMap.filterValues { it == maxPrice }.toMutableMap()
    return goodsMap.keys.toList()[0]
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    var digits = roman.split("")
    if (digits.isEmpty()) return -1
    digits = digits.filter { it != "" }
    val last = digits.size - 1
    val numbers = mutableListOf<Int>()
    for (i in digits.indices) {
        when (digits[i]) {
            "M" -> numbers.add(1000)
            "D" -> numbers.add(500)
            "C" -> if (i != last && digits[i + 1] in listOf("M", "D")) numbers.add(-100) else numbers.add(100)
            "L" -> numbers.add(50)
            "X" -> if (i != last && digits[i + 1] in listOf("C", "L")) numbers.add(-10) else numbers.add(10)
            "V" -> numbers.add(5)
            "I" -> if (i != last && digits[i + 1] in listOf("X", "V")) numbers.add(-1) else numbers.add(1)
            else -> return -1
        }
    }
    return numbers.sum()
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    var commandList = commands.split("")
    commandList = commandList.filter { it != "" }
    if (commandList.isNotEmpty()) {
        if (commandList.any { it !in listOf(">", "<", "+", "-", "[", "]", " ") })
            throw IllegalArgumentException()
        var brackets = commandList.filter { it in listOf("[", "]") }.toList()
        if (brackets.isNotEmpty()) {
            if (brackets.size % 2 != 0 || brackets.count { it == "[" } != brackets.count { it == "]" })
                throw IllegalArgumentException()
            while (brackets.isNotEmpty()) {
                brackets = if (brackets.indexOf("]") == 0)
                    throw IllegalArgumentException()
                else brackets - "[" - "]"
            }
        }
    }

    if (cells < 0) throw IllegalStateException()
    if (cells == 0) {
        if (commandList.isEmpty() || commandList.all { it == " " })
            return emptyList()
        else throw IllegalStateException()
    }

    val result = mutableListOf<Int>()
    for (i in 1..cells) {
        result.add(0)
    }

    var currentCell = cells / 2
    var actionsLeft = limit
    var commandIndex = 0
    var command = commandList[commandIndex]
    val last = commandList.size - 1

    while (actionsLeft > 0) {
        when (command) {
            ">" -> if ((currentCell + 1) >= cells)
                throw IllegalStateException()
            else {
                currentCell++
                actionsLeft--
                commandIndex++
                command = if (commandIndex <= last) commandList[commandIndex] else return result
            }
            "<" -> if ((currentCell - 1) < 0)
                throw IllegalStateException()
            else {
                currentCell--
                actionsLeft--
                commandIndex++
                command = if (commandIndex <= last) commandList[commandIndex] else return result
            }
            "+" -> {
                result[currentCell]++
                actionsLeft--
                commandIndex++
                command = if (commandIndex <= last) commandList[commandIndex] else return result
            }
            "-" -> {
                result[currentCell]--
                actionsLeft--
                commandIndex++
                command = if (commandIndex <= last) commandList[commandIndex] else return result
            }
            "[" -> {
                actionsLeft--
                if (result[currentCell] == 0) {
                    var following = commandList.slice((commandIndex + 1)..last)
                    var count = 1
                    var closing = commandIndex
                    while (count != 0) {
                        if ("[" !in following || following.indexOf("[") > following.indexOf("]")) {
                            count--
                            closing += (following.indexOf("]") + 1)
                            following = following.slice((following.indexOf("]") + 1) until following.size)
                        } else {
                            count++
                            closing += (following.indexOf("[") + 1)
                            following = following.slice((following.indexOf("[") + 1) until following.size)
                        }
                    }
                    commandIndex = closing + 1
                } else {
                    commandIndex++
                }
                command = if (commandIndex <= last) commandList[commandIndex] else return result
            }
            "]" -> {
                actionsLeft--
                if (result[currentCell] != 0) {
                    var preceding = commandList.slice(0 until commandIndex)
                    var count = 1
                    var opening = commandIndex
                    while (count != 0) {
                        opening = if ("]" !in preceding || preceding.lastIndexOf("[") > preceding.lastIndexOf("]")) {
                            count--
                            preceding.lastIndexOf("[")
                        } else {
                            count++
                            preceding.lastIndexOf("]")
                        }
                        preceding = preceding.slice(0 until opening)
                    }
                    commandIndex = opening + 1
                } else {
                    commandIndex++
                }
                command = if (commandIndex <= last) commandList[commandIndex] else return result
            }
            else -> {
                actionsLeft--
                commandIndex++
                command = if (commandIndex <= last) commandList[commandIndex] else return result
            }
        }
    }
    return result
}