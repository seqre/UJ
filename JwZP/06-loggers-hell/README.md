Stwórz program który wczyta z parametrów uruchomieniowych cenę biletu, wiek klienta, jego Id oraz Id naszej firmy. Następnie skorzystaj z klasy `DiscountCalculator` (napisanej przez Wasz zespół dawno temu i przechowywanej we wspólnej dla wielu aplikacji bibliotece, projekt `Tools`) aby policzyć zniżkę dla tego klienta. Finalną cenę biletu oraz oba identyfikatory podaj do klasy `PaymentsService` (dostarczanej nam przez zewnętrzną firmę, implementacja nieznana - projekt `Payments`). Wynik wykonania programu należy wyśwetlić w jednej linii logu.

Według nowych wytycznych frameworkiem do logowania, którego powinniśmy używać w firmie jest *Logback*.
Oczekiwany format logów domyślny Logbacka.

Spraw aby logi zarówno z Twojej aplikacji jak i obu bibliotek wyświetliły się poprawnie. Pamiętaj, że nie możesz modyfikować kodu zewnętrznej firmy (Payments), ale możesz modyfikować kod utworzony w Twojej firmie (Tools) .Zadbaj o to, aby Twój FatJar nie zawierał niepotrzebnych implementacji loggerów.
Skorzystaj z poniższych zależności (`Tools` oraz `Payments` są dołączone do zadania):

```
implementation 'ch.qos.logback:logback-classic:1.2.3'
implementation group: 'com.internal', name: 'Tools', version: '1.0-SNAPSHOT'
implementation group: 'com.external', name: 'Payments', version: '1.0-SNAPSHOT'
```

Do parsownia parametrów wejściowych polecam użycia bilbioteki typu [Apache Commons CLI](http://commons.apache.org/cli/) lub [SpringBoot ApplicationRunner](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/ApplicationRunner.html).

Przykład uruchomienia:
```
java -jar build/libs/loggers-hell-1.0.jar --ticketPrice 10 --customerAge 27 --customerId 1 --companyId 4
```
