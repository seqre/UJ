## Zadanie do wykładu 7: ubezpieczenia na Florydzie. ##

Plik `FL_insurance.csv.zip` zawiera dane o ubezpieczeniach na Florydzie w latach 2011-2012. Jest ich całkiem sporo, toteż plik jest spakowany.

Używając API do odczytu plików, należy wczytać dane z tego pliku (polecam `ZipFile`), i przekonwertować na listę zawierającą te dane w pamięci. Lista ma być typu `List<InsuranceEntry`.

Używając przetwarzania strumieniowego, należy wykonać następujące operacje:

* Wygenerować plik o nazwie `count.txt`, który będzie zawierał ilość krain ("country").
* Wygenerować plik o nazwie `tiv2012.txt`, który będzie zawierał sumę wartości ubezpieczenia wszysstkich nieruchomości za 2012 rok (kolumna "tiv_2012")  
* Wygenerowac plik o nazwie `most_valuable.txt`, zawierający 2 kolumny: "country" oraz "value". Kolumna "country" ma zawierać nazwy 10 krain, dla których wartość ubezbieczenia (total insurance value) sumarycznie wzrosła najwięcej pomiędzy 2011 a 2012 rokiem; nazwy posrtowane malejąco od największego wzrostu wartości do najmniejszego. Kolumna "value" ma zawierać wartość tego przyrostu dla danej krainy, z dokładnością do 2 miejsc dziesiętnych (5 zaokrąglone w gore; 2 miejsca po przecinku zawsze, nawet jeśli jest 0).

Pliki należy generować w głównym katalogu projektu. Wszystkie pliki powinny zawierać wiersz z nagłówkiem. Separator kolumn: przecinek `,`. Separator dziesiętny: kropka `.`

UWAGA: prosze pamiętać, że "hardkodowanie" wyników, pomimo przejścia testów, nie oznacza zaliczenia zadania. Aby otrzymać ocenę BDB, każde przetworzenie należy obsłużyć jednym przebiegiem strumienia.