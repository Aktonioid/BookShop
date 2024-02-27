# Api для онлайнн магазина по продаже книг

**В скором времени добавлю тесты для того чтобы не было регрессии при обновлении**

**В приложении используется бд postgresql**

## Общая информация

Это API интернет-магазина, который следует принципам RESTful, специализирующийся на продаже книг. Для аутентификации используется JWT. Взаимодействует с базой данных PostgreSQL. Реализована слоистая архитектура.

## Что требуется для начала работы 

для начала работы необходимо внести свои данные в три файла:
 * email.properties
 * security.properties
 * databaseConnection.properties

Вместо с {} подставить свои данные

1) email.properties

```
    spring.mail.host=smtp.yandex.ru
    spring.mail.port=465
    spring.mail.username={username@yandex.ru}
    spring.mail.password={userPassword(код доступя для приложений)}
    spring.mail.test-connection=true
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.ssl.enable=true
```

2) security.properties

```
jwt.secret={access token secret key}
refresh.secret={refresh token secret key}
```

3) databaseConnection.properties

```
jdbc.url=jdbc:postgresql://localhost:5432/{databaseName}
jdbc.username={db username}
jdbc.password={user password}
jdbc.drivarClassName=org.postgresql.Driver
```

## Возможности

1) Авторизация с использованием jwt токенов, причем access и refresh токены создаются связанными и можно получить новую пару токенов по refresh токену, который создался в паре с access
2) Фильтрация книг по жанрам
3) Подтверждение почты при регистрации
4) Различные роли для пользователей

## Модели приходящие на контроллер

 **BookModelDto**
  - модель книги
    |Название переменной|Тип данных|Описание|
    |----|----|----|
    |id|UUID|уникальный идентификатор|
    |bookName|String|Название книги|
    |authorName|String|Имя автора книги|
    |publisher|String|Название издателя книги|
    |price|int|Цена|
    |isbn|Strign|Международный стандартный книжный номер|
    |genres|ArrayList<GenreModelDto>|Список жанров, относящихся к книге|
    |description|String|Описание книги|
    |pictureUrl|String|Путь до изображения обложки книги|
    |leftovers|short|Сколько книг осталось на складе|

  **GenreModelDto**
  - модель жанра
    |Название переменной|Тип данных|Описание|
    |----|----|----|
    |id|UUID|уникальный идентификатор|
    |name|String|Имя жанра|

    **OrderModelDto**
    - модель заказа

    |Название переменной|Тип данных|Описание|
    |----|----|----|
    |id|UUID|id заказа|
    |isSend|boolean|Отправлен ли заказ|
    |books|List<OrderPartModelDto>|Список книг, которые есть в заказе|
    |userFullName|String|Полное имя пользователя|
    |deliveryAdress|String|Адрес куда доставлять|
    |sendDate|Date|дата отправки|
    |paymentStatus|boolean|Оплачен ли заказ|
    |userId|UUID|id пользователя, который делал заказ|

    **OrderPartModelDto**
    - часть заказа, содержащая книгу и колличество книг

    |Название переменной|Тип данных|Описание|
    |----|----|----|
    |id|UUID|id части заказа|
    |book|BookModelDto|книга, которую надо доставить|
    |bookCount|int|Сколько книг надо доставить|
    |orderId|UUID|Заказ к которому отосится эта часть|

    **CrateModelDto**
    - Модель корзины пользователя

    |Название переменной|Тип данных|Описание|
    |----|----|----|
    |id|UUID|id корзины(id корзины = id пользователя к которому эта корзина относиться)|
    |books|List<CratePartModelDto>|Книги и их колличество|

    **CratePartModelDto**
    - часть модель части корзины

    |Название переменной|Тип данных|Описание|
    |----|----|----|
    |id|UUID|id части заказа|
    |book|BookModelDto|Модель книги|
    |bookCount|int|колличество копий книги|
    |crateId|UUID|id корзины к которой эта часть относиться|

    **UserModelDto**
    - модель пользователя

    |Название переменной|Тип данных|Описание|
    |----|----|----|
    |id|UUID|id пользователя|
    |username|String|никнейм пользователя|
    |name|String|Имя пользователя|
    |userSurname|String|Фамилия пользователя|
    |email|String|email пользователя|
    |password|String|пароль пользователя|
    |verificationCode|String|Код подтверждения почты|
    |role|Role|роль пользователя|
    |isEmailVerificated|boolean|Подтвердил ли пользователь почту|    

    **RefreshTokenModelDto**
    - модель refresh токена хранящаяся в бд

    |Название переменной|Тип данных|Описание|
    |----|----|----|
    |id|UUID|id токена|
    |token|String|refresh токен|
    |dateExpired|Date|Информация когда токен перестаёт быть валидным|

    **LogInModel**
    - Модель для входа в систему

    |Название переменной|Тип данных|Описание|
    |----|----|----|
    |login|String|логин для входа(email или username)|
    |password|String|пароль|

    **Role**
    - роли, которые могут быть
    
    |Название роли|
    |--|
    |ADMIN|
    |USER|
    

    ## Маппинг 

    **Роут /books**
    - для книг

    |Эндпоинт|Тип запроса|Описание|Входные данные|
    |----|----|----|----|
    |/{page}|Get|Получить список всех книг с пагинацией|path: int page|
    |/pages/all|Get|получение всех страниц при получениии списка всех книг||
    |/book/{bookId}|Get|Получить конкретную книгу по id|path: UUID bookId|
    |/bygenres/{page}|Get| получить книги по жанрам с пагинацией|requestParam: ArrayList<GenreDto>  path:int page| 
    |/pages/genres|Get|получение максимального колличества страниц при поиске по жанрам|requestParam: List<GenreModelDto> genres|
    |/find/author/{page}|Get|Поиск книг по имени автора|path: int page. query: String authorName|
    |/pages/author|Get|получение колличества страниц при поиске по автору|query: String authorName|
    |/find/name/{page}|Get|Поиск книг по названию книги|path: int page.  query: String bookName|
    |/pages/name|Get|получение максимального колличества страниц при поиске по названию книги|query: String bookName|

    **Роут /crate**
    - для корзины

    |Эндпоинт|Тип запроса|Описание|Входные данные|
    |----|----|----|----|
    |/{userId}|Get|Получить корзину по id пользователя|path: UUID userId|
    |/addbook|Put||Body: CratePartModelDto dto  query: UUID userId|
    |/bookcount|Put|добавление еще одной копии книги|body: UUID partId|
    |/update|Put|Обновление корзины|body: CrateModelDto dto|

    **Роут /email**
    - для отправки сообщени на почту

    |Эндпоинт|Тип запроса|Описание|Входные данные|
    |----|----|----|----|
    |send/{id}|Post|Отправка сообщения с кодом подтверждения|pathvariable: UUID userId|
    |verify/{id}|Post|Ввод пользователем код из сообщения и его проверка|PathVariable: UUID userId  query: String verificationCode|


    **Роут /genres**
    - для получения жанров

    |Эндпоинт|Тип запроса|Описание|Входные данные|
    |----|----|----|----|
    |/|Get|Получение всех жанров||
    |/{genreId}|Get|Получение жанра по id|pathVariable: UUID genreId|

    **Роут /orders**
    - для заказов

    |Эндпоинт|Тип запроса|Описание|Входные данные|
    |----|----|----|----|
    |/|Post|Создание нового заказа с подтверждением оплаты(симуляция)|body: OrderModelDto order |
    |/{page}|Get|Получение списка заказов пользователя постранично|query: UUID userId  pathVariable: int page|
    |/order/{orderId}|Get|Получение заказа по id| pathVariable: UUID orferId|

    **Роут /user**
    - для работы с пользователем

    |Эндпоинт|Тип запроса|Описание|Входные данные|
    |----|----|----|----|
    |/update|Put|Обновление пользовательской информации(кроме пароля)|body: UserModelDto userModel|
    |/update/password|Put|обновление пароля пользователя|query: UUID userId, String oldPassword, String newPassword|
    |/register|Post|Регистрация нового аккаунта|body: UserModelDto userModel|
    |/refresh|Post|Получение новой пары access refresh|query: String refreshToken  header: String authorization|
    |/logout|Post|Выход из аккаунта|query String token|
    |/login/username|Post|Вход в аккаунт при помощи имени пользователя|body: LogInModel|
    |/login/email|Post|Вход в аккаунт при помощи почты|body: LogInModel|
    |/forgot|Post|Обновление пароля если пользователь забыл пароль|query: String username|
    |/|Get|получение пользователя по имени пользователя|query: String username|
    |/delete|Delete|Удаление пользователя|query: String accessToken|

    **Роут /admin**
    - Админ панель

    |Эндпоинт|Тип запроса|Описание|Входные данные|
    |----|----|----|----|
    |/books|Post|Создание новой книги|body: BookModelDto bookModel|
    |/books|Put|Обновление информации по книге|body: BookModelDto bookModel|
    |/books|Delete|Удаление книги|query: UUID bookId|
    |/genres|Post|Создание жанра|body: GenreModelDto genreModel|
    |/genres|Delete|Удаление жанра|queryL UUID genreId|
    |/users|Delete|Принудительное удаление пользователя|query: UUID userId|
    |/users/{page}|Get|получение всех зарегестрированных пользователей|path int page|
    |/orders/{page}|Get|Получение всех заказов всех пользователей|path: int page|