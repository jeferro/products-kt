# Products KT

Manage catalog of products of a store.

We apply DDD in this project.

There are 2 aggregates in application: 
* product 
* product review.

And there is 1 projection (read model): 

* product detail: information about product and its reviews to increase performance of product page (backend for frontend). 

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

The next command executes the application locally:

~~~bash
gradle bootRun
~~~
