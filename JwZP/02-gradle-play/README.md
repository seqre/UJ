## Cel zadania:

* Dodać filtering działąjący na podstawie profilu (tak, jak w Mavenie). Przełączanie profilu odbywa się przy użyciu parametru `buildProfile` (opcja -P). Wartość domyślna: `default`. Wartości zmiennych do podstawienia znajdują się w plikach `profile-${buildProfile}.gradle`. *UWAGA*: zawierają prefix `ext`, który powoduje, że wartości znajdą się w tzw. extra properties. 
* Utworzyć task o nazwie 'fatJar', który utworzy pojedyńczy plik jar o nazwie 'fat-jar-${buildProfile}all-1.0.jar', zawierający wszystkie biblioteki.
* Utworzony plik jar musi poprawnie się uruchamiać.

### Podpowiedzi:
* W razie wątpliwości, proszę przeanalizować pliki z testami akceptacyjnymi.
* Zwrócić uwagę na aktualność wyniku builda (UP-TO-DATE) przy zmianie profilu

### Informacje dodatkowe, aby uzyskać wyższą ocenę:
Im mniej rzeczy `hardcoded`, tym wyższa ocena.

Przykład: nazwa pliku jar nie powinna być wpisana na stałe w pliku, ale składana z nazwy projektu, profilu, wersji, i stałej "-all".
