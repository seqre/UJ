## Zadania do wykładu 1.

Projekt zawiera cztery zadania - są one podzielone na trzy klasy.

### Zadanie 1 - Banner
W klasie `Banner` należy zaimplementować metodę `toBanner` w taki sposób, aby zwróciła tekst w parametrze `input` w formie ASCII-artu, w ASCII-artowym foncie banner, o rozmiarze 7.
Wyjściwy ciąg powinien zawierać tylko duże litery (upper-case). Jeśli paramert wejściowy ma wartość `null`, należy zwrócić pustą tablicę.

Kolejne elementy w zwracanej tablicy powinny zawierać cały wiersz do wyświetlenia, zaczynając od górnego przy indeksie 0, kończąc na dolnym. Metoda `main` zawiera kod, któr umożliwia prawidłowe wyświetlenie wygenerowanego tekstu.

Sam font można znaleźć tu: http://patorjk.com/software/taag/#p=display&f=Banner&t=.

Wysokość tekstu: 7, szerokość prawie każdej litery: 7, szerokość litery K: 6, szerokość I: 3, odstęp pomiędzy literami w słowie: 1, szerokość spacji: 4.

W razie wątpliowości co do sposobu realizacji - testy mają rację.

### Zadanie 2 - Quadratic Equation
W klasie `QuadraticEquation`, metoda `findRoots` należy zaimplementować rozwiązywanie równań kwadratowych w postaci `ax2+bx+c=0`.

W razie odnalezienia dwóch pierwiastków należty zwrócić tablicę o rozmiarze 2 z pierwiastkami. Przy jednem pierwisatku - tablica o rozmiarze 1, przy 0 - tablica o rozmiarze 0.

### Zadanie 3 - Reverser
W klasie `Reverser` należy zaimplementować 2 metody.

Metoda `reverse` powinna zwrócić przekazany parametr z wszystkimi znakami w odwrotnej kolejności, z usunięciem białych znaków z początku i końca Stringa.

Metoda `reverseWords` powinna zwrócić przekazany parametr z wszystkim słowami w odwrotnej kolejności, z usunięciem białych znaków z początku i końca Stringa. Słowa są oddzielone spacją.
