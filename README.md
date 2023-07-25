# fightcamp-android
fightcamp take home in android


## **FightCamp take home (Android) 🥊**

Hey! Congratulations on making it to the next step in the interview process. We look forward to having you potentially join the FightCamp family!

## Challenge

Replicate the Workout list feature in the FightCamp app. This popular feature allows the user to browse efficiently the FightCamp workout library. 

## Goals

This challenge has incremental goals that will guide you along the way :

1. Workout Models
2. Workout Card 
3. Workout List 

### Goal #1 - Workout Models

Before starting any UI, the first step is to create a network layer that will allow fetching workouts and trainers. For this challenge, you will have access to a dedicated API: 

**Domain:** [https://android-trial.fightcamp.io](https://android-trial.fightcamp.io/workouts)

* **List Workouts:**  `GET /workouts?offset=<val>`
* **List Trainers:** `GET /trainers`
* **Get Trainer by Id:** `GET /trainers/<id>`

**Workout object**

* **`id`,** Integer → Workout primary key

* **`title`,** String → Title of the workout

* **`added`,** Integer → Unix time in seconds when the workout has been added

* **`preview_img_url`**, String → Url for the workout photo	

* **`trainer_id`**, Integer →  Foreign key for trainers with id

**Trainer object**

* **`id`,** Integer → Trainer primary key

* **`first_name`,** String → First name of the Trainer

* **`last_name`,** String → Last name of the Trainer

**Notes:**

- When listing workouts or trainers, the response should have the following fields:
    
    `items`: Array of objects matching the request
    
    `total_count`: Total number of objects available in the list 
    
- List Workout returns only 10 workouts at a time. To browse the list, you'll need to add query parameters `offset` to specify the starting index.
- Get Trainer by id will only return an object if found and return a 404 response if not found.

In this goal, we are testing your ability to interact with a remote data source, in this case, a REST API  and to create comprehensive data models. 

### Goal #2 - Workout Card

For the next goal, we will be creating the Workout Card component. It represents a condensed view of a workout that the user can select when browsing the library. The following information is displayed:

- Title of the Workout
- Full name of the trainer
- The date that the workout has been added
- Preview picture

Here's the light and dark view : 

![](/.media/1.png)

For more detail about dimensions, see the annex.

### Goal #3 - Workout List

This goal will combine the data you have been able to fetch from the first goal into a list of workout cards that you created during the second goal. Create a scrollable screen that will display a list of Workout Card using the [https://android-trial.fightcamp.io](https://android-trial.fightcamp.io/workouts) data.

![](/.media/2.png)

## **Requirements**

- Must compile
- Kotlin (no Java)
- Jetpack Jompose for the UI
- We recommend MVVM architecture and Android Achitecture Componens (even considering the small size of the app)
- Make sure it looks good in both light and dark mode
- Limit the number of third-party libraries or dependencies to the one you really need. It's okay to use Retrofit, Gson, etc.
- Don't worry about supporting Tablet. Support for Phones only is sufficient.
- Some data structures and algorithms should be used to guarantee optimal performance under heavy workloads.

## **Time allotment**

It should take approximately 4 hours to complete all 3 goals.
Feel free to take extra time to perfect your solution.

## **Project Submission**

- Download this repository to get access to this readme and assets.
- Start a project from scratch supporting Android 8.0 or higher using Android Studio.
- Try to accomplish as many goals as you can! And don't forget to allocate some time to clean up the codebase.
- Make sure it compiles
- Zip the project (with every file) and send it to us.

## **Evaluation Criteria**

- Readability of the code (easy to read, easy to navigate, well structured)
- UI is performant and similar to the given example
- Respect of the architecture (e.g. UI separation from the model)
- The simplicity of the solutions used
- Use of the Kotlin functionalities
- Formatting of the code and code comments
- Good use of algorithms and data structures

**Your canvas is blank, show us how you paint.**

## **Resources Files**

Some resources files are included in this repository for `font`, `colors`, and `dimens`.

## Annex

**Workout Card Dimensions** 

![](/.media/3.png)
