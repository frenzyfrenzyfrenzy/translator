Для запуска:

Либо скачайте джарник из раздела релизов и выполните шаг `3`

либо если хотите собрать с любой ветки:

1. `mvn clean package` 

2. `cd target`

3. `java -jar translator-<version>.jar -Drx2.computation-thread=<n>`

где `version` - версия артефакта, `n` - кол-во потоков для отправки запросов в апи переводчика
 
Убедитесь что у процесса есть права создать файл БД `~/translator`

Пример POST запроса на `http://localhost:8080/translate` (все поля обязательные)

>{
"text": "one two three four five six seven eight nine ten eleven twelve thirteen fourteen",
"from": "en",
"to": "ru"
}

Успешный твет

>{
    "text": "один два три четыре пять шесть семь восемь девять десять одиннадцать двенадцать тринадцать четырнадцать"
}

Ответ с ошибкой

>{"error":"[\"#: required key [to] not found\"]"}

