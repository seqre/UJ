## Zadanie 1: generowanie Stringa w formacie json z kolekcji. ##

Należy zbudwać klasę implemententującą intefejs `uj.java.pwj2019.w3.JsonMapper` tak, aby z dostarczonej mapy budowała String w formacie json.

Metoda `defaultInstance` w interfejsie `JsonMapper` jest fabryką - powinna zwracać instancję stworzonej klasy.

Klucze mapy zawsze będą typu `String`.

Dozwolone type wartości: 
* `String` - należy zamienić na json string
* `int`, `short`, `long`, `byte`, `boolean`, `float`, `double` - należy zamienić na odpowiednie typy json (liczbowy, boolean)
* `Map` - należy zamienić na zagnieżdżony obiekt
* `List` - należy zamienić na tablicę

## Zadanie 2: łączenie list. ##
Należy zaimplementować metodę `uj.java.pwj2019.w3.ListMerger.mergeLists` w taki sposób, aby stworzyła nową, niemodyfikowalną listę zawierającą na przemian elementy z pierwszej i drugiej listy. Metoda nie powinna zwracać nigdy wartości null, tylko pustą listę.
