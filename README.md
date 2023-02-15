# Cern Control Systems coding challenge

[Challenge](./CHALLENGE.md)

## API Specification

| Methods | Urls                    | Request Body                                                                                                                                   | Actions                     |
|---------|-------------------------|------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------|
| POST    | /add-category           | {<br/>"categoryName": "dummy name",<br/>"categoryDescription": "dummy description"<br/>}                                                       | Create a new category       |
| GET     | /all-categories         | -                                                                                                                                              | Get all categories          |
| GET     | /categories/:id         | -                                                                                                                                              | Get category by id          |
| PUT     | /categories/:id         | {<br/>"categoryName": "dummy name",<br/>"categoryDescription": "dummy description"<br/>}                                                       | Update category by id       |
| DELETE  | /categories/:id         | -                                                                                                                                              | Delete category by id       |
| POST    | /add-task               | {<br/>"taskName": "dummy name",<br/>"taskDescription": "dummy description", <br/>"deadline": "yyyy-MM-dd HH:mm:ss", <br/>"categoryId": 1<br/>} | Create a new category       |
| GET     | /all-tasks              | -                                                                                                                                              | Get all tasks               |
| GET     | /tasks/:id              | -                                                                                                                                              | Get task by id              |
| GET     | /category/:id/all-tasks | -                                                                                                                                              | Get tasks by category id    |
| PUT     | /tasks/:id              | {<br/>"taskName": "dummy name",<br/>"taskDescription": "dummy description", <br/>"deadline": "yyyy-MM-dd HH:mm:ss", <br/>"categoryId": 1<br/>} | Update task by id           |
| DELETE  | /tasks/:id              | -                                                                                                                                              | Delete task by id           |
| DELETE  | /tasks                  | -                                                                                                                                              | Delete all tasks            |
| DELETE  | /category/:id/all-tasks | -                                                                                                                                              | Delete tasks by category id |

[Postman Collection with web app requests](./ToDo_app_requests.postman_collection.json)

## How to run

> .\gradlew bootRun
