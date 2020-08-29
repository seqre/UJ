# 07-vet
[![Build Status](https://travis-ci.com/seqre/veterinaryclinic.svg?token=JmUSxPrpzx14kkoZrVNF&branch=master)](https://travis-ci.com/seqre/veterinaryclinic)

## Specification
Należy utworzyć serwis do rejestracji wizyt weterynarza.

Serwis powinien zawierać (wymagania funkcjonalne): 
* Listę klientów (osób), z atrybutami: Imię, Nazwisko, adres domowy, kontakt (email i / lub nr telefonu)
* Listę pacjentów (zwierząt), z atrybutami: Imię, gatunek, data urodzenia (uwaga: może być nieścisła, np tylko rok i miesiąc, albo sam rok!), data odejscia biednego zwierzęcia na tamten świat
* możliwość powiązywania (oraz usuwania powiązań) pacjentów z klientami,
* Wizyty. Atrybuty wizyty: pacjent, godzina rozpoczęcia, czas trwania (możliwe wartości: 15/30/45/60 minut), opis (uzupełniany w czasie wizyty - edycja),status. Wizytę można umawiać tylko na przyszłość od dnia nastepnego (nie można na ten sam dzień), w godzinach od 8:00 do 20:00. Dwie wizyty nie mogą na siebie nachodzić czasowo; przy próbie stworzenia dwóch wizyt obejmujących ten sam czas, należy zwrócić HTTP status 409: conflict (z opisem). Możliwe statusy wizyty: wizyta umówiona, wizyta odbyta, pacjent nie przyszedł.
* Należy umożliwić wyswietlenie wszystkich wizyt danego pacjenta, danego klienta, wszystkie wizyty w danym dniu, itepe - co jeszcze uznacie za przydatne.

Wymagania techniczne:
* API serwisu powinno być RESTowe, kodowanie: UTF-8, format: json.
* Należy zaprojektować API zgodnie z dobrymi praktykami RESTful (https://en.wikipedia.org/wiki/Representational_state_transfer)
* Framework spring jes bardzo mocno zalecany, 
* testy jednostkowe z raportem z testów oraz pokryciem,
* pipeline w Gitlabie, który aplikację skompiluje, przetestuje, oraz wdroży na Heroku
* bazę danych - PostgeSQL na Heroku
* Należy zadbać o właściwe, ładnie sformatowane logowanie oraz o wstrzykiwanie zależności.

