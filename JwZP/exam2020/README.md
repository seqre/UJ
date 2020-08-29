## Warunki zaliczenia egzaminu przedmiotu Jawa w zastosowaniach produkcyjnych

* Należy zaimplementować aplikację webową, zgodnie ze specyfikacją poniżej.
* Egzamin należy pisać przyrostowo: zanim się przejdzie do kolejnych etapów, należy dostarczyć etap na poprzednią ocenę.
* Egzamin należy umieścić w repozytorium na gitlabie (podobnie, jak to było w przypadku ćwiczeń).
* **UWAGA!** Ponieważ testy sa jawne, rozwiązania 'hardcoded' będą ocenione na 2, nawet jeśli testy są zaliczone.

### Wspólne zasady dla wszystkich endpointów:
* W headerze wszystkie odpowiedzi muszą zawierać `Content-type`, wskazujący typ `application/json` oraz kodowanie `UTF-8`.
* Zarówno zapytania jak i odpowiedzi są kodowane UTF-8.
* Należy zaimplementować standardowe kody odpowiedzi dla odpowiednich requestów (`200 OK`, `404 NOT FOUND`, `201 CREATED`).
* Przy zwracaniu kodów błędów, lub odpowiedzi na `POST`/`DELETE`, zawartość (body) odpowiedzi nie jest sprawdzana - jest sprawdzany jedynie staus odpowiedzi.
* Należy pamiętać o idempotencji operacji.

### Ocena 3.0
Należy zaimplementować endpoint `/books`, który będzie dostarczał funkcjonalności dodawania, usuwania, oraz odczytu książek.  
Persystencja nie jest potrzebna.

* `GET /books`  
Pobiera wszystkie książki dostępne w aplikacji. Przykład:  
```json
[
  {
    "id":1,
    "title":"The Lord of The Rings",
    "year":1955
  }
]
```
* `GET /books/{id}`  
Pobiera pojedyńczą książkę, wskazaną przez identyfikator. Przykład:
```json
  {
    "id":1,
    "title":"The Lord of The Rings",
    "year":1955
  }
```
* `POST /books`
Dodaje książkę przekazaną jako body requesta.  
Aplikacja powinna sama ndawać identyfikatory jako kolejne liczby naturalne, zaczynając od 1.  
W body odpowiedzi należy zwrócić utworzoną książkę (z nadanym id).    
Przykład wysyłanego body:
```json
  {
    "title":"The Lord of The Rings",
    "year":1955
  }
```
* `DELETE /books/{id}`  
Usuwa książkę spod wskazanego id. Nie wymaga body.

### Ocena 3.5
Należy dodać endpoint `/authors`, oraz dodać relację książka-autor.

#### Endpointy `/authors`:
* `GET /authors`  
Pobiera wszystkich autorów dostępnych w aplikacji. Przykład:  
```json
[
  {
    "id":1,
    "name":"J.R.R. Tolkien",
    "birthDate":"1892-01-03",
    "deathDate":"1973-09-02"  
  }
]
```
* `GET /authors/{id}`  
Pobiera jednego autora, wskazanego przez identyfikator. Przykład:
```json
  {
    "id":1,
    "name":"J.R.R. Tolkien",
    "birthDate":"1892-01-03",
    "deathDate":"1973-09-02" //null dopuszczalny
  }
```
* `POST /authors`
Dodaje autora przekazanego jako body requesta.  
Aplikacja powinna sama ndawać identyfikatory jako kolejne liczby naturalne, zaczynając od 1.  
W body odpowiedzi należy zwrócić utworzonego autora (z nadanym id).  
Przykład wysyłanego body:
```json
  {
    "name":"J.R.R. Tolkien",
    "birthDate":"1892-01-03",
    "deathDate":"1973-09-02"  
  }
```
* `DELETE /authors/{id}`  
Usuwa autora spod wskazanego id. Nie wymaga body.  
Jeśli dany autor ma przypisane do siebie jakiekolwiek książki, należy zwrócić status `400 BAD REQUEST`.

#### Modyfikacje endpointów `/books`
* `POST /books` 
W body należy umieścić id autora, spośród uprzednio już utworzonych.  
Jeśli w momencie tworzenia autor o podanym id nie istnieje, należy zwrócić status `400 BAD REQUEST`.  
Przykład wysyłanego body:
```json
  {
    "title":"The Lord of The Rings",
    "year":1955,
    "author": 1
  }
```

* `GET /books` oraz `GET /books/{id}`:  
Należy dodać ID autora. Przykład:  
```json
  {
    "id":1,
    "title":"The Lord of The Rings",
    "year":1955,
    "author": 1
  }
```

### Ocena 4.0: wyszukiwanie
* Endpoint `GET /books?byTitle={query}`  
Endpoint wyszukuje książki po tytułach, wg parametru przekazanego w query.  
Query musi zawierać minimum dwie litery. Jeśli jest ich mniej, należy zwrócić status `400 BAD REQUEST`.  
Wyszukiwany tytuł musi zawierać w sobie string przekazany jako query, ignorując wielkość liter.  
Struktura odpowiedzi taka sama, jak endopoint `GET /books`. 

### Ocena 4.5: Persystencja
Należy zaimplementować persystencję, z użyciem relacyjnej bazy danych.  
Polecam bazę typu embedded: H2 lub hsqldb.

### Podniesienie oceny o 0.5 (maksymalnie do 5.0): przegląd jakości kodu i struktury aplikacji.
Aspekty brane pod uwagę przy ocenianiu:
* podział aplikacji na warstwy, prawidłowe rozmieszczenie logiki w warstwach
* wstrzykiwanie zależności
* testowalność kodu (UWAGA: nie wymagam pisania testów jednostkowych, jedynie pisania kodu tak, aby był testowalny!)