Приложение для подсчета выгоды от продажи/покупки валюты (USD).<br>
Данные по валютам берутся из API Yandex (news.yandex.ru).<br>
Суть работы заключается в подсчете выгоды при продаже валюты сегодня, в сравнении с закупочной ценой указываемого пользователем дня.

Для запуска приложения необходимо:
<ul>
    <li>
        Воспользоваться средствами IDE для сборки проекта<br>
        Проект использует библиотеку <a href="https://projectlombok.org/">Lombok</a>, при необходимости надо включить <a href="https://www.jetbrains.com/help/idea/compiler-annotation-processors.html">Annotation processing</a> ("Enable Annotation Processing", этого флага будет достаточно)<br>
        Для сборки проекта достаточно использовать таску "jar".<br>
        Для запуска будет достаточно использовать "bootRun" без аргументов.
    </li>
    <li>
        Использовать уже собранный и подготовленный JAR (<a href="https://yadi.sk/d/8LZuGs3w6eTz7w">Ссылка на YandexDisk</a>)<br>.
        Выполнить команду в папке с файлом java -jar margin-0.0.1-SNAPSHOT.jar
    </li>
</ul>