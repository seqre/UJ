Zaimplementuj procesor adnotacji `MyProcessor` który dla każdej klasy `A` adnotowanej jako `MyComparable` wygeneruje klasę `AComparator` umożliwiającą porównywanie dwóch obiektów klasy `A`. Porównania powinny być realizowane w oparciu o dostępne pola obiektów tej klasy o typach prostych (detale w javadocu tej adnotacji).

Twój procesor powinien obsługiwać także anotację `ComparePriority` umożliwiającą sterowanie istotnością poszczególnych pól (zgodnie z opisem w javadocu tej adnotacji).

Za pomocą Twojego procesora, w trakcie budowania projektu powinna zostać stworzona klasa SecretDataComparator testowana poprzez metodę `main` w klasie `Librarian`. Testy uznaje się za zaliczone jeśli kod wykona się poprawnie - zwracając 0, a nie 1. Wyniki poszczególnych testów są wypisywane na konsoli.
