# Products KT

Manage information about products (apply DDD and Hexagonal Architecture)

* product
* product review
* product detail: projection of product and its reviews to improve times (backend for fronted)

## Local installation

It is necessary ASDF to install project, and plugins below:

~~~bash
asdf plugin add gradle
asdf plugin add java
~~~

Then, we should install mandatory tool:

~~~bash
asdf install
~~~

And build the project:

~~~bash
gradle clean build
~~~


**Local execution**

First, we need run the container of application

~~~bash
docker-compose up -d
~~~

And then, we can execute the application locally:

~~~bash
gradle bootRun
~~~
