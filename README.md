# Laboratorium 4 - Java Persistence API

Zadaną modyfikacją było przerobienie bazy danych z magów i wież na piwa i browary, dzięki czemu moje nazwy są wyjątkowo (nie)trafione. Zadanymi zapytaniami było wypisanie wszystkich piw tańszych od [cena] oraz wypisanie wszystkich piw z [browar] droższych niż [cena]

Nie umiałem poprawnie wykorzystać atrybutów @OnetoMany i @ManyToOne, aby stworzenie piwa z określonego browaru automatycznie wstawiało go do listy piw tego browaru, więc zamiast tego mam metody, które robią te dwie rzeczy i opakowują je w transakcję. 
Wykonywanie tych zapytań nie miało być robione na twardo w kodzie, tylko z konsoli, co byłoby bardzo przydatną wiedzą do posiadania wcześniej niż 30 minut przed końcem zajęć - przez to pętla czytająca zapytania z konsoli była pisana na kolanie i klejona na słowo honoru, więc wygląda jak wygląda. Polecam ją przepisać od zera w swoim zadaniu - powinno być łatwiejsze i szybsze niż ogarnięcie, jak się z tego korzysta xD
