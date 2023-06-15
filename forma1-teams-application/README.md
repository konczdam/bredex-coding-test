# Java Spring boot backend

## Futtatás
Az alkalmazás futtatásához JAVA17 szükséges
```shell
mvn install
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"      
```
(Természetesen a mappában található maven wrapper is használható a futtatáshoz, ez esetben a parancsok elején `./mvnw` kell, hogy szerepeljen)
A futtatáshoz külön konfiguráció nem szükséges. Az alkalmazás a `8080`-as porton próbál futni, annal szabadnak kell lennie, vagy be kell konfigurálni, hogy másikat használjon, de
ezesetben a frontendet is át kell konfigurálni.

## Leírás
A feladat kiírás alapján készült backend alkalmazás, ami egy REST API-t nyújt a frontendnek.

Az alkalmazás inmemory adatbázist használ (H2)

Az alkalmazás megfelőlen van rétegezve (adat, repository, service, controller), mint ahogy egy valós projekten is célszerű.

Az alkalmazás JWT token alapú authentikációt használ.

Az elérhető végpontok használatáról a [http-examples](/http-examples) mappában található `http` fájlban van példa.
(Ezt a fájlformátumot az IntelliJ tudja értelmezni és használni)