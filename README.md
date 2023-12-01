# ParcelTracker

Приложение реализует минимальную функциональность по отслеживанию отправлений и управления их статусами.

Кроме того, реализована массовая загрузка отправленных посылок в виде excel файла.
Загрузка может осуществляться вручную или посредством запланированного метода, который запускается каждые 5 минут, сканирует папку `resources/files_new`
и обрабатывает файлы, которые в ней находятся.

Успешно обработанные файлы перемещаются в папку `resources/files_done`.
Не обработанные файлы перемещаются в папку `resources/files_done`.
При перемещении файлы переименовываются - добавляется временная метка.

В папке `resources/files` лежит пример корректного для загрузки файла.

## Апи:

### Управление отправлениями:

**Получить список всех отправлений**
`GET /api/parcel/get/list`
Список отправлений сортируется по статусу и дате планируемой доставки.

**Изменить статус отправления**
`PUT /api/parcel/update/status`
Тело запроса:
```
{
    "parcelId": 74, - id отправления (обязательный параметр)
    "statusId": 2, - id статуса (обязательный параметр)
    "code": 5591 - код получения (обязателен, только если статус меняется на Доставлен)
}
```
Если статус меняется на Доставлен, у отправления сохраняется текущая дата в качестве даты доставки.


### Управление статусами:

**Получить список доступных статусов**
`GET /api/status/get/list`
Список включает в себя только неудалённые статусы.

**Создать новый статус**
`POST /api/status/create`
В параметрах запроса (`name`) передаётся название статуса.

**Изменить статус**
`PUT /api/status/update/{statusId}`
В адресной строке передаётся id статуса, в параметрах запроса (`name`) - новое название статуса. 

**Удалить статус**
`DELETE /api/status/delete/{statusId}`
В адресной строке передаётся id статуса.
Статус не удаляется из бд, значение его поля `deleted` изменяется на `true`.

**Восстановить статус**
`PUT /api/status/restore/{statusId}`
В адресной строке передаётся id статуса.
Значение поля `deleted` статуса изменяется на `false`.


### Ручная загрузка файла с отправлениями:

`POST /api/upload/file`
В параметрах запроса (`file`) передаётся файл формата .xls или .xlsx


